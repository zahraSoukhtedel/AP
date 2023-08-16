package ir.sharif.math.zahraSoukhtedel.view.messagingPage;

import ir.sharif.math.zahraSoukhtedel.listeners.messagingPage.ChatPanelToMessagingPageListener;
import ir.sharif.math.zahraSoukhtedel.view.FXController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatPanelFXController extends FXController implements Initializable {

    Integer chatID;

    ChatPanelToMessagingPageListener listener;

    @FXML
    private AnchorPane chatPanel;

    @FXML
    private Label chatNameLabel;

    @FXML
    private Label unreadCountLabel;

    @FXML
    public void viewChat() { listener.viewChat(chatID); }

    public AnchorPane getChatPanel() { return chatPanel; }

    public void setChatID(Integer chatID) { this.chatID = chatID; }

    public void setChatNameLabel(String chatName) { chatNameLabel.setText(chatName); }

    public void setUnreadCountLabel(int unreadCount) { unreadCountLabel.setText(String.valueOf(unreadCount)); }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.listener = new ChatPanelToMessagingPageListener();
    }
}
