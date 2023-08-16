package ir.sharif.math.zahraSoukhtedel.view.personalPage.notificationsPage;

import ir.sharif.math.zahraSoukhtedel.view.FXController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class NotificationsFXController extends FXController implements Initializable {

    @FXML
    private TextArea requestMessages;

    @FXML
    private TextArea systemMessages;

    @FXML
    public VBox requests;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        requestMessages.setEditable(false);
        systemMessages.setEditable(false);
    }

    public void addRequestMessage(String message) {
        requestMessages.appendText("\n" + message);
    }

    public void addSystemMessage(String message) {
        systemMessages.appendText("\n" + message);
    }

    public VBox getRequests() { return requests; }

    @Override
    public void clear() {
        requests.getChildren().clear();
        systemMessages.clear();
        requestMessages.clear();
    }
}
