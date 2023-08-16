package ir.sharif.math.zahraSoukhtedel.view.timelinePage;

import ir.sharif.math.zahraSoukhtedel.view.FXController;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;


public class TimelineFXController extends FXController {

    @FXML
    private VBox tweetBox;

    public VBox getTweetBox() {
        return tweetBox;
    }

    @Override
    public void clear() {
        tweetBox.getChildren().clear();
    }
}
