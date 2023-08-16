package ir.sharif.math.zahraSoukhtedel.view.messagingPage;

import ir.sharif.math.zahraSoukhtedel.listeners.messagingPage.EditMessagePageListener;
import ir.sharif.math.zahraSoukhtedel.view.FXController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class EditMessageFXController extends FXController implements Initializable {

    EditMessagePageListener editMessagePageListener;
    Integer messageId;

    @FXML
    private TextArea messageContentField;

    @FXML
    private Label errorLabel;

    public void setErrorLabel(String error) {
        errorLabel.setText(error);
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    @FXML
    public void edit() {
        editMessagePageListener.edit(messageId, messageContentField.getText());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        editMessagePageListener = new EditMessagePageListener();
    }
}
