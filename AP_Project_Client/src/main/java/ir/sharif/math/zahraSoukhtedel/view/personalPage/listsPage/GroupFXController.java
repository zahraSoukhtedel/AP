package ir.sharif.math.zahraSoukhtedel.view.personalPage.listsPage;

import ir.sharif.math.zahraSoukhtedel.listeners.personalPage.listsPage.GroupPageListener;
import ir.sharif.math.zahraSoukhtedel.view.FXController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class GroupFXController extends FXController implements Initializable {
    GroupPageListener groupPageListener;
    Integer group;

    public Integer getGroup() { return group; }

    public void setGroup(Integer group) { this.group = group; }

    @FXML
    private VBox membersBox;

    @FXML
    private TextField addUserField;

    @FXML
    private TextField removeUserField;

    @FXML
    private Label errorLabel;

    @FXML
    public void addUser() {
        groupPageListener.addUser(group, addUserField.getText());
    }

    @FXML
    public void removeGroup() {
        groupPageListener.removeGroup(group);
    }

    @FXML
    public void removeUser() {
        groupPageListener.removeUser(group, removeUserField.getText());
    }

    public VBox getMembersBox() { return membersBox; }

    public void setErrorLabel(String error) { errorLabel.setText(error); }

    @Override
    public void clear() {
        membersBox.getChildren().clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        groupPageListener = new GroupPageListener();
    }
}
