package ir.sharif.math.zahraSoukhtedel.controller.profileView;

import ir.sharif.math.zahraSoukhtedel.models.events.SwitchToProfileType;
import ir.sharif.math.zahraSoukhtedel.util.ImageUtils;
import ir.sharif.math.zahraSoukhtedel.view.Page;
import ir.sharif.math.zahraSoukhtedel.view.ViewManager;
import ir.sharif.math.zahraSoukhtedel.view.explorerPage.ExplorerFXController;
import ir.sharif.math.zahraSoukhtedel.view.messagingPage.MessageViewerFXController;
import ir.sharif.math.zahraSoukhtedel.view.profileView.ProfileFXController;
import ir.sharif.math.zahraSoukhtedel.view.tweets.TweetFXController;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.paint.ImagePattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class ProfileViewController {

    static private final Logger logger = LogManager.getLogger(ProfileViewController.class);

    public void switchToProfilePage(SwitchToProfileType switchToProfileType, boolean exists, boolean mine, String error,
                                    Integer userToBeVisited) {
        switch (switchToProfileType) {
            case USER:
                switchPageByUserID(exists, mine, userToBeVisited);
                break;
            case TWEET:
                switchPageByTweetID(exists, mine, error, userToBeVisited);
                break;
            case MESSAGE:
                switchPageByMessageID(exists, mine, error, userToBeVisited);
                break;
            case USERNAME:
                if(!(ViewManager.getInstance().getCurPage().getFxController() instanceof ExplorerFXController))
                    return;
                ExplorerFXController explorerFXController = (ExplorerFXController) ViewManager.getInstance().
                        getCurPage().getFxController();
                Platform.runLater(() -> explorerFXController.setErrorLabel(error));
        }
    }

    private void switchPageByUserID(boolean exists, boolean mine, Integer userToBeVisited) {
        if (!exists) {
            Platform.runLater(() -> {
                Page deactivatedUserPage = new Page("deactivatedUserPage");
                ViewManager.getInstance().setPage(deactivatedUserPage);
            });
            return;
        }

        if (mine)
            return;
        Platform.runLater(() -> {
            Page profilePage = new Page("profilePage");
            ProfileFXController profileFXController = (ProfileFXController) profilePage.getFxController();
            profileFXController.setUserToVeVisited(userToBeVisited);
            ViewManager.getInstance().setPage(profilePage);
        });
    }

    private void switchPageByTweetID(boolean exists, boolean mine, String error, Integer userToBeVisited) {
        if (!exists) {
            Platform.runLater(() -> {
                Page deactivatedUserPage = new Page("deactivatedUserPage");
                ViewManager.getInstance().setPage(deactivatedUserPage);
            });
            return;
        }
        if (mine) {
            if(!(ViewManager.getInstance().getCurPage().getFxController() instanceof TweetFXController))
                return;
            TweetFXController tweetFXController = (TweetFXController) ViewManager.getInstance().getCurPage().getFxController();
            Platform.runLater(() -> tweetFXController.setVerdictLabelText(error, true));
            return;
        }
        Platform.runLater(() -> {
            Page profilePage = new Page("profilePage");
            ProfileFXController profileFXController = (ProfileFXController) profilePage.getFxController();
            profileFXController.setUserToVeVisited(userToBeVisited);
            ViewManager.getInstance().setPage(profilePage);
        });
    }

    private void switchPageByMessageID(boolean exists, boolean mine, String error, Integer userToBeVisited) {
        if (!exists) {
            Platform.runLater(() -> {
                Page deactivatedUserPage = new Page("deactivatedUserPage");
                ViewManager.getInstance().setPage(deactivatedUserPage);
            });
            return;
        }
        if (mine) {
            if(!(ViewManager.getInstance().getCurPage().getFxController() instanceof MessageViewerFXController))
                return;
            MessageViewerFXController messageViewerFXController = (MessageViewerFXController)
                    ViewManager.getInstance().getCurPage().getFxController();
            Platform.runLater(() -> messageViewerFXController.setErrorLabel(error));
            return;
        }
        Platform.runLater(() -> {
            Page profilePage = new Page("profilePage");
            ProfileFXController profileFXController = (ProfileFXController) profilePage.getFxController();
            profileFXController.setUserToVeVisited(userToBeVisited);
            ViewManager.getInstance().setPage(profilePage);
        });
    }

    public void refresh(String username, String avatarString, String firstname, String lastname, String lastSeen,
                        String bio, String birthdate, String email, String phoneNumber, String blockString,
                        String muteString, String followString) {
        ImageUtils imageUtils = new ImageUtils();
        BufferedImage image = null;
        try {
            image = imageUtils.toBufferedImage(avatarString);
        } catch (IOException e) {
            logger.warn("can't convert byte array to buffered image");
            e.printStackTrace();
        }
        if (!(ViewManager.getInstance().getCurPage().getFxController() instanceof ProfileFXController))
            return;

        ProfileFXController profileFXController = (ProfileFXController)
                ViewManager.getInstance().getCurPage().getFxController();
        profileFXController.getAvatar().setFill(new ImagePattern(SwingFXUtils.toFXImage(image, null)));

        Platform.runLater(() -> {
            profileFXController.setUsernameLabel(username);
            profileFXController.setFirstnameLabel(firstname);
            profileFXController.setLastnameLabel(lastname);
            profileFXController.setLastSeenLabel(lastSeen);
            profileFXController.setBioLabel(bio);
            profileFXController.setBirthDateLabel(birthdate);
            profileFXController.setEmailLabel(email);
            profileFXController.setPhoneNumberLabel(phoneNumber);
            profileFXController.setBlockButtonText(blockString);
            profileFXController.setMuteButtonText(muteString);
            profileFXController.setFollowButton(followString);
        });
    }
}
