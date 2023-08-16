package ir.sharif.math.zahraSoukhtedel.controller.enterPage;

import ir.sharif.math.zahraSoukhtedel.controller.ClientHandler;
import ir.sharif.math.zahraSoukhtedel.database.Connector;
import ir.sharif.math.zahraSoukhtedel.exceptions.DatabaseDisconnectException;
import ir.sharif.math.zahraSoukhtedel.models.User;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.response.ShowErrorResponse;
import ir.sharif.math.zahraSoukhtedel.response.enterPage.EnterResponse;
import ir.sharif.math.zahraSoukhtedel.util.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SignInController {

    static private final Logger logger = LogManager.getLogger(SignUpController.class);

    private final ClientHandler clientHandler;

    public SignInController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public Response login(String username, String password) {
        try {
            Config errorsConfig = Config.getConfig("serverConfig").getProperty(Config.class,"signInPage");
            User user = Connector.getInstance().getUserByUsername(username);
            if (user == null) {
                logger.info(errorsConfig.getProperty("noUserError"));
                return new EnterResponse(false, errorsConfig.getProperty("noUserError"));
            }
            if (!user.getPassword().equals(password)) {
                logger.info(errorsConfig.getProperty("passNotMatch"));
                return new EnterResponse(false, errorsConfig.getProperty("passNotMatch"));
            }
            user.setLastSeen(null);
            user.setActive(true);
            Connector.getInstance().save(user);

            clientHandler.setUser(user);

            logger.info(String.format("user %s signed in.", user.getUsername()));
            return new EnterResponse(true, "");
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("serverConfig").getProperty(Config.class,"server").getProperty("databaseDisconnectError"));
        }
    }
}
