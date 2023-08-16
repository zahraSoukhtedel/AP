package ir.sharif.math.zahraSoukhtedel.listeners.tweets;

import ir.sharif.math.zahraSoukhtedel.controller.Client;
import ir.sharif.math.zahraSoukhtedel.models.events.SwitchToProfileType;
import ir.sharif.math.zahraSoukhtedel.models.events.TweetPageEventType;
import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.request.messagingPage.messageSendingPage.ForwardTweetRequest;
import ir.sharif.math.zahraSoukhtedel.request.messagingPage.messageSendingPage.SaveTweetRequest;
import ir.sharif.math.zahraSoukhtedel.request.profileView.SwitchToProfilePageRequest;
import ir.sharif.math.zahraSoukhtedel.request.tweets.TweetActionRequest;
import ir.sharif.math.zahraSoukhtedel.util.Config;
import ir.sharif.math.zahraSoukhtedel.view.Page;
import ir.sharif.math.zahraSoukhtedel.view.ViewManager;
import ir.sharif.math.zahraSoukhtedel.view.tweets.NewTweetFXController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TweetPageListener {

    static private final Logger logger = LogManager.getLogger(TweetPageListener.class);

    public void addComment(Integer tweetID) {
        Page newTweetPage = new Page("newTweetPage");
        ViewManager.getInstance().setPage(newTweetPage);
        NewTweetFXController newTweetFXController = (NewTweetFXController) newTweetPage.getFxController();
        newTweetFXController.setUpPost(tweetID);
        newTweetFXController.setTitleLabel(Config.getConfig("clientConfig").getProperty(Config.class, "tweets").getProperty(String.class, "commentTitle"));
    }

    public void checkProfile(Integer tweetID) {
        Request request = new SwitchToProfilePageRequest(SwitchToProfileType.TWEET, tweetID, "");
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
    }

    public void like(Integer tweetID) {
        Request request = new TweetActionRequest(TweetPageEventType.LIKE, tweetID);
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
    }

    public void report(Integer tweetID) {
        Request request = new TweetActionRequest(TweetPageEventType.REPORT_SPAM, tweetID);
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
    }

    public void retweet(Integer tweetID) {
        Request request = new TweetActionRequest(TweetPageEventType.RETWEET, tweetID);
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
    }

    public void save(Integer tweetID) {
        Request request = new SaveTweetRequest(tweetID);
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
    }

    public void forward(Integer tweetID) {
        Request request = new ForwardTweetRequest(tweetID);
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
    }
}
