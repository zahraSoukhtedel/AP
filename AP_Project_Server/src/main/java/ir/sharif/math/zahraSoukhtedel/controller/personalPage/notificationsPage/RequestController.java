package ir.sharif.math.zahraSoukhtedel.controller.personalPage.notificationsPage;

import ir.sharif.math.zahraSoukhtedel.controller.ClientHandler;
import ir.sharif.math.zahraSoukhtedel.database.Connector;
import ir.sharif.math.zahraSoukhtedel.exceptions.DatabaseDisconnectException;
import ir.sharif.math.zahraSoukhtedel.models.User;
import ir.sharif.math.zahraSoukhtedel.models.events.RequestAnswerType;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.response.ShowErrorResponse;
import ir.sharif.math.zahraSoukhtedel.util.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RequestController {

    static private final Logger logger = LogManager.getLogger(RequestController.class);

    private final ClientHandler clientHandler;

    public RequestController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public Response handle(RequestAnswerType requestAnswerType, Integer requesterID) {
        try {
            User user = clientHandler.getUser();
            User requester = Connector.getInstance().fetch(User.class, requesterID);
            switch (requestAnswerType) {
                case ACCEPT:
                    requester.addToFollowings(user.getId());
                    requester.addToRequestNotifications(
                            String.format("user %s accepted your follow request!", user.getUsername()));
                    logger.info(String.format("%s accepted %s's request.", user.getUsername(), requester.getUsername()));
                    user.addToFollowers(requester.getId());
                    user.removeFromRequests(requester.getId());
                    Connector.getInstance().save(user);
                    Connector.getInstance().save(requester);
                    break;
                case REJECT:
                    requester.addToRequestNotifications(
                            String.format("user %s rejected your follow request!", user.getUsername()));
                    logger.info(String.format("%s rejected %s's request.", user.getUsername(), requester.getUsername()));
                    user.removeFromRequests(requester.getId());
                    Connector.getInstance().save(user);
                    Connector.getInstance().save(requester);
                    break;
                case MUTE_REJECT:
                    user.removeFromRequests(requester.getId());
                    Connector.getInstance().save(user);
                    logger.info(String.format("%s rejected %s's request.", user.getUsername(), requester.getUsername()));
                    break;
            }
            return null;
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("serverConfig").getProperty(Config.class,"server").getProperty("databaseDisconnectError"));
        }
    }
}
