package ir.sharif.math.zahraSoukhtedel.controller.tweets;

import ir.sharif.math.zahraSoukhtedel.models.viewModels.ViewTweet;
import ir.sharif.math.zahraSoukhtedel.util.ImageUtils;
import ir.sharif.math.zahraSoukhtedel.view.Page;
import ir.sharif.math.zahraSoukhtedel.view.ViewManager;
import ir.sharif.math.zahraSoukhtedel.view.tweets.TweetFXController;
import ir.sharif.math.zahraSoukhtedel.view.tweets.TweetPanelFXController;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TweetManager {

    private static TweetManager instance;
    static private final Logger logger = LogManager.getLogger(TweetManager.class);

    public static TweetManager getInstance() {
        if (instance == null)
            instance = new TweetManager();
        return instance;
    }

    public AnchorPane makeTweetPanel(String retweetString, String tweetContent, LocalDateTime tweetDateTime, Integer tweetId,
                                     boolean myTweets) {
        Page tweetPanel = new Page("tweetPanel");
        TweetPanelFXController tweetPanelFXController = (TweetPanelFXController) tweetPanel.getFxController();

        tweetPanelFXController.setTweetContent(tweetContent);
        tweetPanelFXController.setTweetDate(tweetDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        tweetPanelFXController.setMyTweets(myTweets);
        tweetPanelFXController.setRetweetLabel(retweetString);
        tweetPanelFXController.setTweetID(tweetId);

        return tweetPanelFXController.getTweetPanel();
    }

    public void refresh(String tweetContent, String tweetDate, String retweetString, String tweetImage, int likeNumbers,
                        List<ViewTweet> viewTweetList, String likeButtonText) {
        if (!(ViewManager.getInstance().getCurPage().getFxController() instanceof TweetFXController))
            return;
        TweetFXController tweetFXController = (TweetFXController) ViewManager.getInstance().getCurPage().getFxController();
        ImageUtils imageUtils = new ImageUtils();
        BufferedImage image = null;
        try {
            image = imageUtils.toBufferedImage(tweetImage);
        } catch (IOException e) {
            logger.warn("can't convert byte array to buffered image");
            e.printStackTrace();
        }
        if (image != null)
            tweetFXController.getPhotoBox().setFill(new ImagePattern(SwingFXUtils.toFXImage(image, null)));

        Platform.runLater(() -> {
            tweetFXController.clear();
            tweetFXController.setTweetContent(tweetContent);
            tweetFXController.setTweetDate(tweetDate);
            tweetFXController.setRetweetLabel(retweetString);
            tweetFXController.setLikesCounter(likeNumbers);
            for (ViewTweet viewTweet : viewTweetList)
                tweetFXController.getCommentsBox().getChildren().add(new AnchorPane(TweetManager.getInstance().
                        makeTweetPanel(viewTweet.getRetweetString(), viewTweet.getTweetContent(),
                                viewTweet.getTweetDateTime(), viewTweet.getTweetId(), viewTweet.isMyTweets())));
            tweetFXController.setLikeButtonText(likeButtonText);
        });
    }

    public void applyTweetActionResponse(String verdict, boolean isError) {
        if (!(ViewManager.getInstance().getCurPage().getFxController() instanceof TweetFXController))
            return;
        TweetFXController tweetFXController = (TweetFXController) ViewManager.getInstance().getCurPage().getFxController();
        Platform.runLater(() -> tweetFXController.setVerdictLabelText(verdict, isError));
    }
}
