package ir.sharif.math.zahraSoukhtedel.view.personalPage.listsPage;

import ir.sharif.math.zahraSoukhtedel.listeners.personalPage.listsPage.ViewPanelListener;
import ir.sharif.math.zahraSoukhtedel.view.FXController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewPanelFXController extends FXController implements Initializable {

    Integer userToBeVisitedID;
    ViewPanelListener viewPanelListener;

    public void setUserToBeVisitedID(Integer userToBeVisitedID) {
        this.userToBeVisitedID = userToBeVisitedID;
    }

    @FXML
    private AnchorPane viewPane;

    @FXML
    private Label usernameLabel;

    public void setUsernameLabel(String username) {
        usernameLabel.setText(username);
    }

    public AnchorPane getViewPane() { return viewPane; }

    @FXML
    public void view() {
        viewPanelListener.viewEventOccurred(userToBeVisitedID);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewPanelListener = new ViewPanelListener();
    }
}
