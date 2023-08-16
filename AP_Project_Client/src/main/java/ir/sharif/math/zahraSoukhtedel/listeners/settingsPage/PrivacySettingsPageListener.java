package ir.sharif.math.zahraSoukhtedel.listeners.settingsPage;


import ir.sharif.math.zahraSoukhtedel.controller.Client;
import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.request.settingsPage.DeactivateRequest;
import ir.sharif.math.zahraSoukhtedel.request.settingsPage.EditPrivacySettingsRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PrivacySettingsPageListener {

    static private final Logger logger = LogManager.getLogger(PrivacySettingsPageListener.class);

    public void edit(String password, String lastSeen, boolean isPrivate) {
        Request request = new EditPrivacySettingsRequest(isPrivate, lastSeen, password);
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
        Client.getUserOffline().setNewPassword(password);
        Client.getUserOffline().setLastSeenType(lastSeen);
        Client.getUserOffline().setPublicData(!isPrivate);
    }

    public void deactivate() {
        Request request = new DeactivateRequest();
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
    }
}
