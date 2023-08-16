package ir.sharif.math.zahraSoukhtedel.view.personalPage.notificationsPage;

import ir.sharif.math.zahraSoukhtedel.listeners.personalPage.notificationsPage.RequestHandlingListener;
import ir.sharif.math.zahraSoukhtedel.models.events.RequestAnswerType;
import ir.sharif.math.zahraSoukhtedel.view.FXController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class RequestFXController extends FXController implements Initializable {

    RequestHandlingListener requestHandlingListener;

    Integer requesterID;

    @FXML
    private AnchorPane requestPane;

    public AnchorPane getRequestPane() { return requestPane; }

    @FXML
    private Label requester;

    public void setRequester(String requesterName) {
        requester.setText(requesterName);
    }

    @FXML
    public void accept() {
        requestHandlingListener.eventOccurred(RequestAnswerType.ACCEPT, requesterID);
    }

    @FXML
    public void muteReject() {
        requestHandlingListener.eventOccurred(RequestAnswerType.MUTE_REJECT, requesterID);
    }

    @FXML
    public void reject() {
        requestHandlingListener.eventOccurred(RequestAnswerType.REJECT, requesterID);
    }

    public void setRequesterID(Integer requesterID) {
        this.requesterID = requesterID;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        requestHandlingListener = new RequestHandlingListener();
    }
}
