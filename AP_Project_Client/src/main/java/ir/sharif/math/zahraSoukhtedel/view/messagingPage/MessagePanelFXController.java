package ir.sharif.math.zahraSoukhtedel.view.messagingPage;

import ir.sharif.math.zahraSoukhtedel.controller.Client;
import ir.sharif.math.zahraSoukhtedel.controller.hyperlink.HyperlinkController;
import ir.sharif.math.zahraSoukhtedel.listeners.messagingPage.MessagePanelListener;
import ir.sharif.math.zahraSoukhtedel.models.events.SwitchToProfileType;
import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.request.profileView.SwitchToProfilePageRequest;
import ir.sharif.math.zahraSoukhtedel.view.FXController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import lombok.Getter;
import lombok.Setter;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class MessagePanelFXController extends FXController implements Initializable {
    MessagePanelListener messagePanelListener;
    Integer messageID;

    public void setMessageID(Integer messageID) {
        this.messageID = messageID;
    }

    @FXML
    private AnchorPane messagePanel;

    @FXML
    private Label senderLabel;

//    @FXML
//    private Label messageTextLabel;

    @FXML
    private Label messageDateLabel;

    @FXML
    private Label messageStateLabel;

    @FXML
    private TextFlow textFlow;

    @FXML
    public void view() {
        messagePanelListener.view(messageID);
    }

    public void setTextFlow(String messageText) {
        if(messageText.charAt(0) == '&' || messageText.charAt(0) == '#' || messageText.charAt(0) == '$'){
            Hyperlink hyperlink = new Hyperlink(messageText);
            textFlow.getChildren().add(hyperlink);
            HyperlinkController.checkHyperlink(messageText,hyperlink);
        }
        else{
            textFlow.getChildren().add(new Text(messageText));
        }
    }

    public void setSenderLabel(String senderText) {
        senderLabel.setText(senderText);
    }

//    public void setMessageTextLabel(String messageText) {
//        messageTextLabel.setText(messageText);
//    }

    public void setMessageDateLabel(String messageDate) {
        messageDateLabel.setText(messageDate);
    }

    public void setMessageStateLabel(String messageState) {
        messageStateLabel.setText(messageState);
    }

    public AnchorPane getMessagePanel() {
        return messagePanel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messagePanelListener = new MessagePanelListener();
    }
}
