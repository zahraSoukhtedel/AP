package ir.sharif.math.zahraSoukhtedel.view.tweets;

import ir.sharif.math.zahraSoukhtedel.listeners.tweets.TweetPageListener;
import ir.sharif.math.zahraSoukhtedel.util.Config;
import ir.sharif.math.zahraSoukhtedel.view.FXController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class TweetFXController extends FXController implements Initializable {
    Integer tweetID;
    boolean myTweets;
    TweetPageListener tweetPageListener;

    @FXML
    private Rectangle photoBox;

    @FXML
    private TextArea tweetContent;

    @FXML
    private Label retweetLabel;

    @FXML
    private Label tweetDate;

    @FXML
    private Label likesCounter;

    @FXML
    private VBox commentsBox;

    @FXML
    private Label verdictLabel;

    @FXML
    private Button likeButton;

    @FXML
    private Label tweetIdLabel;

    public void setTweetIdLabel(Integer tweetID){this.tweetIdLabel.setText(tweetID.toString());}

    public void setTweetID(Integer tweetID) {
        this.tweetID = tweetID;
    }

    public VBox getCommentsBox() { return commentsBox; }

    public Integer getTweetID() { return tweetID; }

    public boolean isMyTweets() { return myTweets; }

    public Rectangle getPhotoBox() { return photoBox; }

    public void setMyTweets(boolean myTweets) {
        this.myTweets = myTweets;
    }

    public void setTweetContent(String content) { tweetContent.setText(content); }

    public void setTweetDate(String date) {
        tweetDate.setText(date);
    }

    public void setRetweetLabel(String content) {
        retweetLabel.setText(content);
    }

    public void setLikesCounter(int likeNumbers) { likesCounter.setText("likes: " + likeNumbers); }

    public void setVerdictLabelText(String content, boolean isError) {
        if(isError)
            verdictLabel.setTextFill(Color.valueOf(Config.getConfig("clientConfig").getProperty(Config.class,"tweets").getProperty(String.class, "errorColor")));
        else
            verdictLabel.setTextFill(Color.valueOf(Config.getConfig("clientConfig").getProperty(Config.class,"tweets").getProperty(String.class, "acceptColor")));
        verdictLabel.setText(content);
    }

    public void setLikeButtonText(String text) {
        likeButton.setText(text);
    }

    @FXML
    public void addComment() {
        tweetPageListener.addComment(tweetID);
    }

    @FXML
    public void checkUserProfile() {
        tweetPageListener.checkProfile(tweetID);
    }

    @FXML
    public void like() {
        tweetPageListener.like(tweetID);
    }

    @FXML
    public void reportSpam() {
        tweetPageListener.report(tweetID);
    }

    @FXML
    public void retweet() {
        tweetPageListener.retweet(tweetID);
    }

    @FXML
    public void save() {
        tweetPageListener.save(tweetID);
    }

    @FXML
    public void forward() {
        tweetPageListener.forward(tweetID);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tweetPageListener = new TweetPageListener();
        photoBox.setFill(Color.TRANSPARENT);
        tweetContent.setEditable(false);
    }

    @Override
    public void clear() {
        commentsBox.getChildren().clear();
    }
}
