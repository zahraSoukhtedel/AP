package ir.sharif.math.zahraSoukhtedel.controller.tweets;

import ir.sharif.math.zahraSoukhtedel.exceptions.DatabaseDisconnectException;
import ir.sharif.math.zahraSoukhtedel.models.events.TweetPageEventType;
import ir.sharif.math.zahraSoukhtedel.database.Connector;
import ir.sharif.math.zahraSoukhtedel.database.ImageLoader;
import ir.sharif.math.zahraSoukhtedel.models.User;
import ir.sharif.math.zahraSoukhtedel.models.media.Tweet;
import ir.sharif.math.zahraSoukhtedel.models.viewModels.ViewTweet;
import ir.sharif.math.zahraSoukhtedel.response.BackResponse;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.response.ShowErrorResponse;
import ir.sharif.math.zahraSoukhtedel.response.tweets.TweetActionResponse;
import ir.sharif.math.zahraSoukhtedel.response.tweets.UpdateTweetPageResponse;
import ir.sharif.math.zahraSoukhtedel.util.Config;
import ir.sharif.math.zahraSoukhtedel.util.ImageUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TweetManager {

    private static TweetManager instance;

    static private final Logger logger = LogManager.getLogger(TweetManager.class);

    public static TweetManager getInstance() {
        if(instance == null)
            instance = new TweetManager();
        return instance;
    }

    public List<ViewTweet> getViewTweets(User user, List<Tweet> tweets, boolean myTweets) throws DatabaseDisconnectException {
        List<ViewTweet> viewTweets = new ArrayList<>();
        for (Tweet tweet : tweets) {
            String retweetString;
            User tweetWriter = Connector.getInstance().fetch(User.class, tweet.getWriter());
            if(myTweets && tweet.getUpPost() == null && !user.getId().equals(tweet.getWriter()))
                retweetString = "you retweeted from " + tweetWriter.getUsername();
            else
                retweetString = tweetWriter.getUsername();
            viewTweets.add(new ViewTweet(retweetString, tweet.getContent(), tweet.getDateTime(), tweet.getId(),
                    myTweets));
        }
        return viewTweets;
    }

    public List<Tweet> validation(User user, List<Tweet> tweets, boolean firstLayer) throws DatabaseDisconnectException {
        List<Tweet> validatedTweets = new ArrayList<>();
        for (Tweet tweet : tweets) {
            User writer = Connector.getInstance().fetch(User.class, tweet.getWriter());
            if (writer == null || !writer.isActive())
                continue;
            if (writer.equals(user)) {
                if (!firstLayer)
                    validatedTweets.add(tweet);
                continue;
            }
            if (user.getMutedUsers().contains(writer.getId()))
                continue;
            if (writer.isPrivate() && !user.getFollowings().contains(writer.getId()))
                continue;
            if (tweet.getSpamReports() > Config.getConfig("tweets").getProperty(Integer.class, "maxSpam"))
                continue;
            if (!validatedTweets.contains(tweet))
                validatedTweets.add(tweet);
        }
        return validatedTweets;
    }

    public Response getUpdate(Integer tweetId, User user, boolean myTweets) {
        try {
            Tweet tweet = Connector.getInstance().fetch(Tweet.class, tweetId);
            String tweetContent = tweet.getContent();
            String tweetDate = tweet.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            String retweetString;
            User tweetWriter = Connector.getInstance().fetch(User.class, tweet.getWriter());
            if (myTweets && tweet.getUpPost() == null && !user.getId().equals(tweet.getWriter()))
                retweetString = "you retweeted from " + tweetWriter.getUsername();
            else
                retweetString = tweetWriter.getUsername();

            ImageLoader imageLoader = new ImageLoader();
            BufferedImage bufferedImage = imageLoader.getByID(tweet.getImage());

            ImageUtils imageUtils = new ImageUtils();
            String tweetImage;
            try {
                tweetImage = imageUtils.toString(bufferedImage, "png");
            } catch (IOException e) {
                logger.warn("can't convert buffered image to byte array");
                e.printStackTrace();
                return null;
            }

            int likeNumbers = tweet.getLikeNumbers();

            List<Tweet> curComments = new ArrayList<>();
            for (Integer commentId : tweet.getComments())
                curComments.add(Connector.getInstance().fetch(Tweet.class, commentId));

            List<Tweet> comments = validation(user, curComments, false);
            Tweet.sortByDateTime(comments);

            String likeButtonText;
            if (user.getLikedTweets().contains(tweet.getId()))
                likeButtonText = "dislike";
            else
                likeButtonText = "like";

            return new UpdateTweetPageResponse(tweetContent, tweetDate, retweetString, tweetImage, likeNumbers,
                    getViewTweets(user, comments, myTweets), likeButtonText);
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("server").getProperty("databaseDisconnectError"));
        }
    }

    public Response applyTweetAction(TweetPageEventType tweetPageEventType, User user, Integer tweetId) {
        try {
            switch (tweetPageEventType) {
                case LIKE:
                    return like(user, tweetId);
                case RETWEET:
                    return retweet(user, tweetId);
                case REPORT_SPAM:
                    return reportSpam(user, tweetId);
                default:
                    return null;
            }
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("server").getProperty("databaseDisconnectError"));
        }
    }

    private Response like(User user, Integer tweetID) throws DatabaseDisconnectException {

        Tweet tweet = Connector.getInstance().fetch(Tweet.class, tweetID);

        if (user.getLikedTweets().contains(tweet.getId())) {
            dislike(user, tweet);
            logger.info(String.format("user %s disliked tweet %s.", user.getUsername(), tweet.getId()));
            return new TweetActionResponse(Config.getConfig("tweets").
                    getProperty(String.class, "successfulDislike"), false);
        }
        else {
            if (tweet.getWriter().equals(user.getId()))
                return new TweetActionResponse(Config.getConfig("tweets").
                        getProperty(String.class, "selfLikeError"), true);
            else {
                like(user, tweet);
                logger.info(String.format("user %s liked tweet %s.", user.getUsername(), tweet.getId()));
                return new TweetActionResponse(Config.getConfig("tweets").
                        getProperty(String.class, "successfulLike"), false);
            }
        }
    }

    private void like(User user, Tweet tweet) throws DatabaseDisconnectException {
        tweet.addLike(user.getId());
        Connector.getInstance().save(tweet);

        user.addToLikedTweets(tweet.getId());
        Connector.getInstance().save(user);
    }

    private void dislike(User user, Tweet tweet) throws DatabaseDisconnectException {
        tweet.removeLike(user.getId());
        Connector.getInstance().save(tweet);

        user.removeFromLikedTweets(tweet.getId());
        Connector.getInstance().save(user);
    }

    private Response retweet(User user, Integer tweetID) throws DatabaseDisconnectException {

        Tweet tweet = Connector.getInstance().fetch(Tweet.class, tweetID);

        if (tweet.getUpPost() != null) {
            logger.info(String.format("user %s wants to retweet a comment", user.getUsername()));
            return new TweetActionResponse(Config.getConfig("tweets").
                    getProperty(String.class, "retweetCommentError"), true);
        }
        else if (user.getTweets().contains(tweet.getId())) {
            logger.info(String.format("user %s wants to retweet a tweet which has already retweeted.",
                    user.getUsername()));
            return new TweetActionResponse(Config.getConfig("tweets").
                    getProperty(String.class, "alreadyRetweetedError"), true);
        }
        else {
            user.addToTweets(tweet.getId());
            Connector.getInstance().save(user);

            logger.info(String.format("user %s retweeted tweet %s.", user.getUsername(), tweet.getId()));
            return new TweetActionResponse(Config.getConfig("tweets").
                    getProperty(String.class, "successfulRetweet"), false);
        }
    }

    private Response reportSpam(User user, Integer tweetID) throws DatabaseDisconnectException {

        Tweet tweet = Connector.getInstance().fetch(Tweet.class, tweetID);

        if (user.getReportedSpamTweets().contains(tweetID))
            return new TweetActionResponse(Config.getConfig("tweets").
                    getProperty(String.class, "alreadyReportedError"), true);
        else {
            reportSpam(user, tweet);
            return new BackResponse();
        }
    }

    private void reportSpam(User user, Tweet tweet) throws DatabaseDisconnectException {
        user.addToReportedSpamTweets(tweet.getId());
        Connector.getInstance().save(user);

        tweet.reportSpam();
        Connector.getInstance().save(tweet);
    }


}
