package ir.sharif.math.zahraSoukhtedel.view.tweets;

import ir.sharif.math.zahraSoukhtedel.listeners.tweets.TweetPanelListener;
import ir.sharif.math.zahraSoukhtedel.view.FXController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class TweetPanelFXController extends FXController implements Initializable {

    TweetPanelListener tweetPanelListener;

    Integer tweetID;
    boolean myTweets;

    public void setMyTweets(boolean myTweets) {
        this.myTweets = myTweets;
    }

    public void setTweetID(Integer tweetID) {
        this.tweetID = tweetID;
    }

    @FXML
    private AnchorPane tweetPanel;

    @FXML
    private Label tweetContent;

    @FXML
    private Label tweetDate;

    @FXML
    private Label retweetLabel;

    @FXML
    public void view() {
        tweetPanelListener.eventOccurred(tweetID, myTweets);
    }

    public void setTweetContent(String content) {
        tweetContent.setText(content);
    }

    public void setTweetDate(String date) {
        tweetDate.setText(date);
    }

    public void setRetweetLabel(String content) {
        retweetLabel.setText(content);
    }

    public AnchorPane getTweetPanel() {
        return tweetPanel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tweetPanelListener = new TweetPanelListener();
    }
}
