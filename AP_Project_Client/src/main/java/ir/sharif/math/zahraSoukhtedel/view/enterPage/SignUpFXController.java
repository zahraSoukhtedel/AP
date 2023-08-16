package ir.sharif.math.zahraSoukhtedel.view.enterPage;

import ir.sharif.math.zahraSoukhtedel.listeners.enterPage.SignUpFormListener;
import ir.sharif.math.zahraSoukhtedel.util.Config;
import ir.sharif.math.zahraSoukhtedel.view.FXController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpFXController extends FXController implements Initializable {

    @FXML
    private TextField username;

    @FXML
    private TextField firstname;

    @FXML
    private TextField lastname;

    @FXML
    private TextField bio;

    @FXML
    private DatePicker birthdate;

    @FXML
    private TextField email;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField password;

    @FXML
    private ChoiceBox<String> lastSeenChoice;

    private final String[] lastSeenChoices = Config.getConfig("clientConfig").getProperty(Config.class,"signUpPage").
            getPropertyArray(String.class, "allOptions");

    @FXML
    private CheckBox publicData;

    @FXML
    private Label errorLabel;

    private SignUpFormListener signUpFormListener;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lastSeenChoice.getItems().addAll(lastSeenChoices);
        lastSeenChoice.setValue(Config.getConfig("clientConfig").getProperty(Config.class, "signUpPage").getProperty("everyoneOption"));
        signUpFormListener = new SignUpFormListener();
    }

    @FXML
    public void register() {
        signUpFormListener.register(username.getText(), firstname.getText(),
                lastname.getText(), bio.getText(), birthdate.getValue(), email.getText(), phoneNumber.getText(),
                password.getText(), publicData.isSelected(), lastSeenChoice.getValue());
    }

    @FXML
    public void setError(String error) {
        errorLabel.setText(error);
    }
}
