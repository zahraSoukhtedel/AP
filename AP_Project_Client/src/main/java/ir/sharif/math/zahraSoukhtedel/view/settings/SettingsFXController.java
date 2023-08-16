package ir.sharif.math.zahraSoukhtedel.view.settings;

import ir.sharif.math.zahraSoukhtedel.listeners.settingsPage.SettingsPageListener;
import ir.sharif.math.zahraSoukhtedel.view.FXController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsFXController extends FXController implements Initializable {

    SettingsPageListener settingsPageListener;

    @FXML
    public void deleteAccount() {
        settingsPageListener.stringEventOccurred("deleteAccount");
    }

    @FXML
    public void logout() {
        settingsPageListener.stringEventOccurred("logout");
    }

    @FXML
    public void privacySettings() {
        settingsPageListener.stringEventOccurred("privacySettings");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        settingsPageListener = new SettingsPageListener();
    }
}
