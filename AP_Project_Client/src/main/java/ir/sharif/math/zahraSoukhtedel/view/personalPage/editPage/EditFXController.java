package ir.sharif.math.zahraSoukhtedel.view.personalPage.editPage;

import ir.sharif.math.zahraSoukhtedel.listeners.personalPage.editPage.EditPageListener;
import ir.sharif.math.zahraSoukhtedel.view.FXController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EditFXController extends FXController implements Initializable {

    static private final Logger logger = LogManager.getLogger(EditFXController.class);

    BufferedImage avatar;

    @FXML
    private Label usernameField;

    @FXML
    private TextField firstnameField;

    @FXML
    private TextField lastnameField;

    @FXML
    private TextField bioField;

    @FXML
    private DatePicker birthdateField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private Label lastSeenField;

    @FXML
    private Label accountPrivacyField;

    @FXML
    private Label dataPrivacyField;

    @FXML
    private Label errorLabel;

    @FXML
    public void setUsernameField(String username) {
        usernameField.setText(username);
    }

    @FXML
    public void setFirstnameField(String firstname) {
        firstnameField.setText(firstname);
    }

    @FXML
    public void setLastnameField(String lastname) {
        lastnameField.setText(lastname);
    }

    @FXML
    public void setBioField(String bio) {
        bioField.setText(bio);
    }

    @FXML
    public void setBirthdateField(LocalDate birthDate) {
        birthdateField.setValue(birthDate);
    }

    @FXML
    public void setEmailField(String email) {
        emailField.setText(email);
    }

    @FXML
    public void setPhoneNumberField(String phoneNumber) {
        phoneNumberField.setText(phoneNumber);
    }

    @FXML
    public void setError(String error) {
        errorLabel.setText(error);
    }

    @FXML
    public void setLastSeenField(String lastSeen) {
        lastSeenField.setText(lastSeen);
    }

    @FXML
    public void setAccountPrivacyField(String accountPrivacy) {
        accountPrivacyField.setText(accountPrivacy);
    }

    @FXML
    public void setDataPrivacyField(String dataPrivacy) {
        dataPrivacyField.setText(dataPrivacy);
    }

    @FXML
    public void edit() {
        editPageListener.eventOccurred(firstnameField.getText(), lastnameField.getText(), bioField.getText(),
                birthdateField.getValue(), emailField.getText(), phoneNumberField.getText(), avatar);
    }

    @FXML
    public void changeAvatar() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().addAll(extFilterPNG);
        File file = fileChooser.showOpenDialog(null);

        try {
            if(file == null)
                avatar = null;
            else
                avatar = ImageIO.read(file);
            logger.info("loaded image successfully");
        } catch (IOException e) {
            e.printStackTrace();
            logger.warn("an error occurred while trying to load image.");
        }
    }

    private EditPageListener editPageListener;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        editPageListener = new EditPageListener();
    }
}
