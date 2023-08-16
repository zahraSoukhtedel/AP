package ir.sharif.math.zahraSoukhtedel.view.messagingPage.messageSendingPage;

import ir.sharif.math.zahraSoukhtedel.listeners.messagingPage.messageSendigPage.ChatSelectingPageListener;
import ir.sharif.math.zahraSoukhtedel.view.FXController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ChatSelectingFXController extends FXController implements Initializable {

    ChatSelectingPageListener chatSelectingPageListener;
    Integer messageID;

    List<Integer> selectedGroups = new ArrayList<>();
    List<Integer> selectedChats = new ArrayList<>();

    public void setMessageID(Integer messageID) { this.messageID = messageID; }

    public void addGroup(Integer group) { selectedGroups.add(group); }

    public void removeGroup(Integer group) { selectedGroups.remove(group); }

    public void addChat(Integer chat) { selectedChats.add(chat); }

    public void removeChat(Integer chat) { selectedChats.remove(chat); }

    public List<Integer> getSelectedGroups() { return selectedGroups; }

    public List<Integer> getSelectedChats() { return selectedChats; }

    @FXML
    private VBox chatsBox;

    @FXML
    private VBox groupsBox;

    public VBox getChatsBox() { return chatsBox; }

    public VBox getGroupsBox() { return groupsBox; }

    @Override
    public void clear() {
        chatsBox.getChildren().clear();
        groupsBox.getChildren().clear();
    }

    @FXML
    public void send() {
        chatSelectingPageListener.send(messageID, selectedChats, selectedGroups);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chatSelectingPageListener = new ChatSelectingPageListener();
    }
}
