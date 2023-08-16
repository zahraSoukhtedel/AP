package ir.sharif.math.zahraSoukhtedel.view.messagingPage.messageSendingPage;

import ir.sharif.math.zahraSoukhtedel.listeners.messagingPage.messageSendigPage.ChatPanelToChatPageListener;
import ir.sharif.math.zahraSoukhtedel.view.FXController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ChatSelectingPanelFXController extends FXController {
    Integer chatID;
    ChatPanelToChatPageListener chatPanelToChatPageListener;

    @FXML
    private Label chatNameLabel;

    @FXML
    private AnchorPane chatSelectingPanel;

    @FXML
    public void toggle() {
        chatPanelToChatPageListener.switchState(chatID);
    }

    public void setChatID(Integer chatID) { this.chatID = chatID; }

    public void setChatPanelToChatPageListener(ChatPanelToChatPageListener chatPanelToChatPageListener) {
        this.chatPanelToChatPageListener = chatPanelToChatPageListener;
    }

    public void setChatNameLabel(String chatName) { chatNameLabel.setText(chatName); }

    public AnchorPane getChatSelectingPanel() { return chatSelectingPanel; }
}
