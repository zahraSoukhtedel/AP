package ir.sharif.math.zahraSoukhtedel.view.enterPage;

import ir.sharif.math.zahraSoukhtedel.listeners.enterPage.SignInFormListener;
import ir.sharif.math.zahraSoukhtedel.view.FXController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SignInFXController extends FXController implements Initializable {
    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private Label errorLabel;

    private SignInFormListener signInFormListener;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        signInFormListener = new SignInFormListener();
    }

    public void login() {
        signInFormListener.login(username.getText(), password.getText());
    }

    @FXML
    public void setError(String error) {
        errorLabel.setText(error);
    }
}
