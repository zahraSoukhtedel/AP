package ir.sharif.math.zahraSoukhtedel.controller.personalPage;

import ir.sharif.math.zahraSoukhtedel.controller.tweets.TweetManager;
import ir.sharif.math.zahraSoukhtedel.models.viewModels.ViewTweet;
import ir.sharif.math.zahraSoukhtedel.util.ImageUtils;
import ir.sharif.math.zahraSoukhtedel.view.ViewManager;
import ir.sharif.math.zahraSoukhtedel.view.personalPage.MyFXController;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class MyPageController {

    static private final Logger logger = LogManager.getLogger(MyPageController.class);

    public void refresh(String bytes, List<ViewTweet> viewTweetList) {
        ImageUtils imageUtils = new ImageUtils();
        BufferedImage image = null;
        try {
            image = imageUtils.toBufferedImage(bytes);
        } catch (IOException e) {
            logger.warn("can't convert byte array to buffered image");
            e.printStackTrace();
        }
        if (!(ViewManager.getInstance().getCurPage().getFxController() instanceof MyFXController))
            return;
        MyFXController myFXController = (MyFXController) ViewManager.getInstance().getCurPage().getFxController();
        myFXController.getAvatar().setFill(new ImagePattern(SwingFXUtils.toFXImage(image, null)));

        //adding tweets to personal page.
        Platform.runLater(() -> {
            myFXController.clear();
            for (ViewTweet viewTweet : viewTweetList)
                myFXController.getTweetBox().getChildren().add(new AnchorPane(TweetManager.getInstance().
                        makeTweetPanel(viewTweet.getRetweetString(), viewTweet.getTweetContent(),
                                viewTweet.getTweetDateTime(), viewTweet.getTweetId(), viewTweet.isMyTweets())));
        });
    }
}
