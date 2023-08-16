package ir.sharif.math.zahraSoukhtedel.listeners.settingsPage;

import ir.sharif.math.zahraSoukhtedel.controller.Client;
import ir.sharif.math.zahraSoukhtedel.request.DeleteAccountRequest;
import ir.sharif.math.zahraSoukhtedel.request.LogoutRequest;
import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.request.settingsPage.SwitchToPrivacySettingsPageRequest;
import ir.sharif.math.zahraSoukhtedel.view.Page;
import ir.sharif.math.zahraSoukhtedel.view.ViewManager;
import ir.sharif.math.zahraSoukhtedel.view.settings.PrivacySettingsFXController;
import javafx.application.Platform;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SettingsPageListener {

    static private final Logger logger = LogManager.getLogger(SettingsPageListener.class);

    public void stringEventOccurred(String event) {
        Request request;
        switch (event) {
            case "deleteAccount":
                if(Client.isOnline()){
                    request = new DeleteAccountRequest();
                    logger.info(String.format("client requested %s", request));
                    Client.getClient().addRequest(request);
                }
                break;
            case "logout":
                if(Client.isOnline()){
                    request = new LogoutRequest(false);
                    logger.info(String.format("client requested %s", request));
                    Client.getClient().addRequest(request);
                }
                else {
                    System.exit(0);
                }
                break;
            case "privacySettings":
                if(Client.isOnline()){
                    request = new SwitchToPrivacySettingsPageRequest();
                    logger.info(String.format("client requested %s", request));
                    Client.getClient().addRequest(request);
                }
                else {
                    Platform.runLater(() -> {
                        ViewManager.getInstance().setPage(new Page("privacySettingsPage"));
                        PrivacySettingsFXController privacySettingsFXController = (PrivacySettingsFXController)
                                ViewManager.getInstance().getCurPage().getFxController();
                        privacySettingsFXController.setPrivacy(!Client.getUserOffline().isPublicData());
                        privacySettingsFXController.setLastSeen(Client.getUserOffline().getLastSeenType());
                        privacySettingsFXController.setPasswordField(Client.getUserOffline().getPassword());
                    });
                }
                break;
        }
    }
}
