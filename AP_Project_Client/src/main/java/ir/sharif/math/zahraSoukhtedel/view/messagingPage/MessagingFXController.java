package ir.sharif.math.zahraSoukhtedel.view.messagingPage;

import ir.sharif.math.zahraSoukhtedel.listeners.messagingPage.MessagingPageListener;
import ir.sharif.math.zahraSoukhtedel.view.FXController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MessagingFXController extends FXController implements Initializable {
    Integer selectedChatID;
    MessagingPageListener messagingPageListener;

    @FXML
    private VBox chatList;

    @FXML
    private VBox chatBox;

    @FXML
    private ScrollPane scroll;

    @FXML
    private Label chatNameLabel;

    @FXML
    public void chatGroup() {
        messagingPageListener.stringEventOccurred("chatGroup");
    }

    @FXML
    public void sendMessage() {
        messagingPageListener.stringEventOccurred("sendMessage");
    }

    @Override
    public void clear() {
        chatBox.getChildren().clear();
        setSelectedChatID(null);
        chatNameLabel.setText("");
    }

    public Integer getSelectedChatID() { return selectedChatID; }

    public void setSelectedChatID(Integer selectedChatID) { this.selectedChatID = selectedChatID; }

    public ScrollPane getScroll() { return scroll; }

    public VBox getChatList() { return chatList; }

    public VBox getChatBox() { return chatBox; }

    public void setChatNameLabel(String chatName) { chatNameLabel.setText(chatName); }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messagingPageListener = new MessagingPageListener();
    }
}
