package ir.sharif.math.zahraSoukhtedel.view.mainPage;

import ir.sharif.math.zahraSoukhtedel.listeners.mainPage.MainPageListener;
import ir.sharif.math.zahraSoukhtedel.view.FXController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MainFXController extends FXController implements Initializable {

    MainPageListener mainPageListener;

    @FXML
    private Label errorLabel;

    @FXML
    public void personalPage() {
        mainPageListener.stringEventOccurred("myPage");
    }

    @FXML
    public void settings() { mainPageListener.stringEventOccurred("settings"); }

    @FXML
    public void timeline() { mainPageListener.stringEventOccurred("timeline"); }

    @FXML
    public void explorer() { mainPageListener.stringEventOccurred("explorer"); }

    @FXML
    public void messaging() { mainPageListener.stringEventOccurred("messaging"); }

    @FXML
    public void online() {mainPageListener.stringEventOccurred("online");}

    @FXML
    public void setError(String error){errorLabel.setText(error);}


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainPageListener = new MainPageListener();
    }
}
