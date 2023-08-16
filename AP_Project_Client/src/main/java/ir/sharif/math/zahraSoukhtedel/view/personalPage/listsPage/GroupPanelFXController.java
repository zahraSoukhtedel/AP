package ir.sharif.math.zahraSoukhtedel.view.personalPage.listsPage;

import ir.sharif.math.zahraSoukhtedel.listeners.personalPage.listsPage.GroupPanelListener;
import ir.sharif.math.zahraSoukhtedel.view.FXController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class GroupPanelFXController extends FXController implements Initializable {

    GroupPanelListener groupPanelListener;

    Integer group;

    @FXML
    private AnchorPane groupPane;

    @FXML
    private Label groupNameLabel;

    public void setGroupNameLabel(String groupName) {
        groupNameLabel.setText(groupName);
    }

    public Integer getGroup() { return group; }

    public void setGroup(Integer group) { this.group = group; }

    public AnchorPane getGroupPane() { return groupPane; }

    @FXML
    public void view() {
        groupPanelListener.viewEventOccurred(group);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        groupPanelListener = new GroupPanelListener();
    }
}
