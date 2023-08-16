package ir.sharif.math.zahraSoukhtedel.view.personalPage;

import ir.sharif.math.zahraSoukhtedel.listeners.personalPage.MyPageListener;
import ir.sharif.math.zahraSoukhtedel.view.FXController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class MyFXController extends FXController implements Initializable {
    @FXML
    private Circle avatar;

    @FXML
    private VBox tweetBox;

    MyPageListener myPageListener;

    public Circle getAvatar() {
        return avatar;
    }

    @FXML
    public void newTweet() {
        myPageListener.stringEventOccurred("newTweet");
    }

    @FXML
    public void edit() {
        myPageListener.stringEventOccurred("edit");
    }

    @FXML
    public void notifications() { myPageListener.stringEventOccurred("notifications"); }

    @FXML
    public void lists() { myPageListener.stringEventOccurred("lists"); }

    @Override
    public void clear() { tweetBox.getChildren().clear(); }

    public VBox getTweetBox() { return tweetBox; }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        myPageListener = new MyPageListener();
    }
}
