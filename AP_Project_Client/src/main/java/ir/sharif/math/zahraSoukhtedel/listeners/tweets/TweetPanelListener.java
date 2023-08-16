package ir.sharif.math.zahraSoukhtedel.listeners.tweets;

import ir.sharif.math.zahraSoukhtedel.view.Page;
import ir.sharif.math.zahraSoukhtedel.view.ViewManager;
import ir.sharif.math.zahraSoukhtedel.view.tweets.TweetFXController;

public class TweetPanelListener {
    public void eventOccurred(Integer tweetID, boolean myTweets) {
        Page tweetPage = new Page("tweetPage");
        TweetFXController tweetFXController = (TweetFXController) tweetPage.getFxController();

        tweetFXController.setTweetIdLabel(tweetID);
        tweetFXController.setTweetID(tweetID);
        tweetFXController.setMyTweets(myTweets);

        ViewManager.getInstance().setPage(tweetPage);
    }
}
