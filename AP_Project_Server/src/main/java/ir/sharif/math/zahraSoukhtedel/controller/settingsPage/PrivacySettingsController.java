package ir.sharif.math.zahraSoukhtedel.controller.settingsPage;

import ir.sharif.math.zahraSoukhtedel.controller.ClientHandler;
import ir.sharif.math.zahraSoukhtedel.database.Connector;
import ir.sharif.math.zahraSoukhtedel.exceptions.DatabaseDisconnectException;
import ir.sharif.math.zahraSoukhtedel.response.BackResponse;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.models.User;
import ir.sharif.math.zahraSoukhtedel.response.ShowErrorResponse;
import ir.sharif.math.zahraSoukhtedel.response.settingsPage.SwitchToPrivacySettingsPageResponse;
import ir.sharif.math.zahraSoukhtedel.util.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PrivacySettingsController {
    static private final Logger logger = LogManager.getLogger(PrivacySettingsController.class);

    private final ClientHandler clientHandler;

    public PrivacySettingsController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public Response getInfoToSwitch() {
        User user = clientHandler.getUser();
        return new SwitchToPrivacySettingsPageResponse(user.isPrivate(), user.getLastSeenType(), user.getPassword());
    }

    public Response deactivate() {
        try {
            User user = clientHandler.getUser();
            logger.info(String.format("user %s deactivated his/her account.", user.getUsername()));
            user.setActive(false);
            Connector.getInstance().save(user);

            SettingsController settingsController = new SettingsController(clientHandler);
            return settingsController.logout(false);
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("serverConfig").getProperty(Config.class,"server").getProperty("databaseDisconnectError"));
        }
    }

    public Response editPrivacySettingsResponse(boolean isPrivate, String lastSeenType, String password) {
        try {
            User user = clientHandler.getUser();
            logger.info(String.format("user %s changed his/her privacy settings", user.getUsername()));
            user.setPassword(password);
            user.setLastSeenType(lastSeenType);
            user.setPrivate(isPrivate);
            Connector.getInstance().save(user);
            SettingsController settingsController = new SettingsController(clientHandler);
            //***************************************************
            return new BackResponse();
            //****************************************************
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("serverConfig").getProperty(Config.class,"server").getProperty("databaseDisconnectError"));
        }
    }
}
