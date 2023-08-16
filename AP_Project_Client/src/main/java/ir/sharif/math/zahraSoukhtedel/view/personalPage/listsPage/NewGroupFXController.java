package ir.sharif.math.zahraSoukhtedel.view.personalPage.listsPage;

import ir.sharif.math.zahraSoukhtedel.listeners.personalPage.listsPage.NewGroupListener;
import ir.sharif.math.zahraSoukhtedel.view.FXController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class NewGroupFXController extends FXController implements Initializable {

    NewGroupListener newGroupListener;

    @FXML
    private TextField groupNameField;

    @FXML
    private Label errorLabel;

    @FXML
    public void makeGroup() {
        newGroupListener.eventOccurred(groupNameField.getText());
    }

    public void setErrorLabel(String error) {
        errorLabel.setText(error);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newGroupListener = new NewGroupListener();
    }
}
