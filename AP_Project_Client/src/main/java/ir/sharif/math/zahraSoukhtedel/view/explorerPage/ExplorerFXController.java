package ir.sharif.math.zahraSoukhtedel.view.explorerPage;

import ir.sharif.math.zahraSoukhtedel.listeners.explorerPage.ExplorerListener;
import ir.sharif.math.zahraSoukhtedel.view.FXController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ExplorerFXController extends FXController implements Initializable {
    ExplorerListener explorerListener;

    @FXML
    private VBox tweetBox;

    @FXML
    private TextField usernameLabel;

    @FXML
    private Label errorLabel;

    @FXML
    public void search() {
        explorerListener.search(usernameLabel.getText());
    }

    public VBox getTweetBox() { return tweetBox; }

    public void setErrorLabel(String error) { errorLabel.setText(error); }

    @Override
    public void clear() {
        tweetBox.getChildren().clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        explorerListener = new ExplorerListener();
    }
}
