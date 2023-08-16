package ir.sharif.math.zahraSoukhtedel.controller.timelinePage;

import ir.sharif.math.zahraSoukhtedel.controller.ClientHandler;
import ir.sharif.math.zahraSoukhtedel.controller.tweets.TweetManager;
import ir.sharif.math.zahraSoukhtedel.database.Connector;
import ir.sharif.math.zahraSoukhtedel.exceptions.DatabaseDisconnectException;
import ir.sharif.math.zahraSoukhtedel.models.media.Tweet;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.response.ShowErrorResponse;
import ir.sharif.math.zahraSoukhtedel.response.timelinePage.UpdateTimelinePageResponse;
import ir.sharif.math.zahraSoukhtedel.models.User;
import ir.sharif.math.zahraSoukhtedel.util.Config;

import java.util.ArrayList;
import java.util.List;

public class TimelineController {
    private final ClientHandler clientHandler;

    public TimelineController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public Response getUpdate() {
        try {
            User user = clientHandler.getUser();
            //getting valid tweets
            List<Tweet> tweets = new ArrayList<>();
            for (Integer followingID : user.getFollowings()) {
                for (Integer tweetID : Connector.getInstance().fetch(User.class, followingID).getTweets())
                    tweets.add(Connector.getInstance().fetch(Tweet.class, tweetID));
                for (Integer tweetID : Connector.getInstance().fetch(User.class, followingID).getLikedTweets())
                    tweets.add(Connector.getInstance().fetch(Tweet.class, tweetID));
            }
            List<Tweet> validatedTweets = TweetManager.getInstance().validation(user, tweets, true);
            Tweet.sortByLikeNumbers(validatedTweets);
            return new UpdateTimelinePageResponse(TweetManager.getInstance().getViewTweets(user, validatedTweets, false));
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("serverConfig").getProperty(Config.class,"server").getProperty("databaseDisconnectError"));
        }
    }
}
