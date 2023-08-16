package ir.sharif.math.zahraSoukhtedel.view.messagingPage.messageSendingPage;

import ir.sharif.math.zahraSoukhtedel.listeners.messagingPage.messageSendigPage.NewMessageListener;
import ir.sharif.math.zahraSoukhtedel.view.FXController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class MessageSendingFXController extends FXController implements Initializable {
    static private final Logger logger = LogManager.getLogger(MessageSendingFXController.class);
    NewMessageListener newMessageListener;

    BufferedImage messageImage;
    Integer receiverID;

    public void setReceiverID(Integer receiverID) {
        this.receiverID = receiverID;
    }

    @FXML
    private TextArea messageContent;

    @FXML
    private Label errorLabel;
//#############################
    @FXML
    private CheckBox timingMessage;

    @FXML
    private DatePicker sendingDate;

    @FXML
    private TextField sendingTime;
//##################################
    @FXML
    public void attach() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().addAll(extFilterPNG);
        File file = fileChooser.showOpenDialog(null);

        try {
            if(file == null)
                messageImage = null;
            else
                messageImage = ImageIO.read(file);
            logger.info("loaded image successfully");
        } catch (IOException e) {
            e.printStackTrace();
            logger.warn("an error occurred while trying to load image.");
        }
    }

    @FXML
    public void send() {
        //##################################################
        LocalDateTime dateTime = null;
        boolean b = timingMessage.isSelected();
        if (b){
            LocalDate date = sendingDate.getValue();
            String time = sendingTime.getText();
            String [] A = time.split(":");
            int hour =Integer.parseInt(A[0]);
            int minute = Integer.parseInt(A[1]);
            LocalTime as = LocalTime.of(hour, minute, 0, 0);
            dateTime = LocalDateTime.of(date, as);
        }
        newMessageListener.send(messageImage, messageContent.getText(), receiverID, dateTime, b);
        //####################################################
    }

    public void setErrorLabel(String error) {
        errorLabel.setText(error);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newMessageListener = new NewMessageListener();
    }
}
