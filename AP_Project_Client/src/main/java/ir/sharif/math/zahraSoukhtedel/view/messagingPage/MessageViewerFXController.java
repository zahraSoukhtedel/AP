package ir.sharif.math.zahraSoukhtedel.view.messagingPage;

import ir.sharif.math.zahraSoukhtedel.listeners.messagingPage.MessageViewerPageListener;
import ir.sharif.math.zahraSoukhtedel.view.FXController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class MessageViewerFXController extends FXController implements Initializable {

    MessageViewerPageListener messageViewerPageListener;
    Integer messageID;

    public void setMessageID(Integer messageID) { this.messageID = messageID; }

    public Integer getMessageID() { return messageID; }

    @FXML
    private Rectangle photoBox;

    @FXML
    private TextArea messageContentField;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Label messageDateLabel;

    @FXML
    private Label senderLabel;

    @FXML
    private Label errorLabel;

    @FXML
    public void delete() {
        messageViewerPageListener.delete(messageID);
    }

    @FXML
    public void edit() {
        messageViewerPageListener.edit(messageID);
    }

    @FXML
    public void forward() {
        messageViewerPageListener.forward(messageID);
    }

    @FXML
    public void checkUserProfile() {
        messageViewerPageListener.checkProfile(messageID);
    }

    public void setErrorLabel(String error) { errorLabel.setText(error); }

    public void deactivateButtons() {
        editButton.setVisible(false);
        deleteButton.setVisible(false);
        editButton.setDisable(true);
        deleteButton.setDisable(true);
    }

    public void setMessageContentField(String messageContent) { this.messageContentField.setText(messageContent); }

    public void setSenderLabel(String senderText) { senderLabel.setText(senderText); }

    public void setMessageDateLabel(String messageDate) { messageDateLabel.setText(messageDate); }

    public Rectangle getPhotoBox() { return photoBox; }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        photoBox.setFill(Color.TRANSPARENT);
        messageContentField.setEditable(false);
        messageViewerPageListener = new MessageViewerPageListener();
    }
}
