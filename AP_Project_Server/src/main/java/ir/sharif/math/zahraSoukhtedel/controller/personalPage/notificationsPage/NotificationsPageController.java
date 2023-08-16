package ir.sharif.math.zahraSoukhtedel.controller.personalPage.notificationsPage;

import ir.sharif.math.zahraSoukhtedel.controller.ClientHandler;
import ir.sharif.math.zahraSoukhtedel.database.Connector;
import ir.sharif.math.zahraSoukhtedel.exceptions.DatabaseDisconnectException;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.response.ShowErrorResponse;
import ir.sharif.math.zahraSoukhtedel.response.personalPage.notificationsPage.UpdateNotificationsPageResponse;
import ir.sharif.math.zahraSoukhtedel.models.User;
import ir.sharif.math.zahraSoukhtedel.models.viewModels.ViewUser;
import ir.sharif.math.zahraSoukhtedel.util.Config;

import java.util.ArrayList;
import java.util.List;

public class NotificationsPageController {

    private final ClientHandler clientHandler;

    public NotificationsPageController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public Response getUpdate() {
        try {
            User user = clientHandler.getUser();
            List<String> requestMessages = user.getRequestNotifications();
            List<String> systemMessages = user.getNotifications();
            List<ViewUser> requests = new ArrayList<>();
            for (Integer id : user.getRequests()) {
                User currentUser = Connector.getInstance().fetch(User.class, id);
                requests.add(new ViewUser(currentUser.getUsername(), currentUser.getId(), currentUser.isActive()));
            }
            return new UpdateNotificationsPageResponse(requestMessages, systemMessages, requests);
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("serverConfig").getProperty(Config.class,"server").getProperty("databaseDisconnectError"));
        }
    }
}
