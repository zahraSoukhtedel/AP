package ir.sharif.math.zahraSoukhtedel.view.profileView;

import ir.sharif.math.zahraSoukhtedel.listeners.profileView.ProfilePageListener;
import ir.sharif.math.zahraSoukhtedel.view.FXController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileFXController extends FXController implements Initializable {
    ProfilePageListener profilePageListener;
    Integer userToVeVisited;

    public Integer getUserToVeVisited() {
        return userToVeVisited;
    }

    public void setUserToVeVisited(Integer userToVeVisited) {
        this.userToVeVisited = userToVeVisited;
    }

    @FXML
    private Circle avatar;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label firstnameLabel;

    @FXML
    private Label lastnameLabel;

    @FXML
    private Label lastSeenLabel;

    @FXML
    private Label bioLabel;

    @FXML
    private Label birthDateLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label phoneNumberLabel;

    @FXML
    private Button muteButton;

    @FXML
    private Button blockButton;

    @FXML
    private Button followButton;

    public Circle getAvatar() {
        return avatar;
    }
    
    public void setUsernameLabel(String username) { usernameLabel.setText(username); }
    
    public void setFirstnameLabel(String firstname) { firstnameLabel.setText(firstname); }
    
    public void setLastnameLabel(String lastname) { lastnameLabel.setText(lastname); }

    public void setLastSeenLabel(String lastSeen) { lastSeenLabel.setText(lastSeen); }

    public void setBioLabel(String bio) { bioLabel.setText(bio); }

    public void setBirthDateLabel(String birthDate) { birthDateLabel.setText(birthDate); }

    public void setEmailLabel(String email) { emailLabel.setText(email); }

    public void setPhoneNumberLabel(String phoneNumber) { phoneNumberLabel.setText(phoneNumber); }

    public void setBlockButtonText(String text) { blockButton.setText(text); }

    public void setMuteButtonText(String text) { muteButton.setText(text); }

    public void setFollowButton(String text) { followButton.setText(text); }

    @FXML
    public void blockHandling() {
        profilePageListener.blockHandle(userToVeVisited);
    }

    @FXML
    public void followHandling() {
        profilePageListener.followHandle(userToVeVisited);
    }

    @FXML
    public void muteHandling() {
        profilePageListener.muteHandle(userToVeVisited);
    }

    @FXML
    public void messageHandling() {
        profilePageListener.messageHandle(userToVeVisited);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        profilePageListener = new ProfilePageListener();
    }
}
