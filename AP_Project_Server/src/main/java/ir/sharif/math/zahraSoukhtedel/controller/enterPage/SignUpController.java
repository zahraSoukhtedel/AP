package ir.sharif.math.zahraSoukhtedel.controller.enterPage;
import ir.sharif.math.zahraSoukhtedel.controller.ClientHandler;
import ir.sharif.math.zahraSoukhtedel.database.Connector;
import ir.sharif.math.zahraSoukhtedel.database.ImageLoader;
import ir.sharif.math.zahraSoukhtedel.exceptions.DatabaseDisconnectException;
import ir.sharif.math.zahraSoukhtedel.models.Chat;
import ir.sharif.math.zahraSoukhtedel.models.ChatState;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.response.ShowErrorResponse;
import ir.sharif.math.zahraSoukhtedel.response.enterPage.EnterResponse;
import ir.sharif.math.zahraSoukhtedel.controller.DataValidator;
import ir.sharif.math.zahraSoukhtedel.models.User;
import ir.sharif.math.zahraSoukhtedel.util.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

public class SignUpController {

    private final ClientHandler clientHandler;

    public SignUpController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    static private final Logger logger = LogManager.getLogger(SignUpController.class);

    public Response register(String username, String firstname, String lastname, String bio, LocalDate birthDate,
                             String email, String phoneNumber, String password, boolean publicData, String lastSeenType) {
        try {
            String error;
            DataValidator signUpValidator = new DataValidator();

            error = signUpValidator.validateUsername(username);

            if (error.equals(""))
                error = signUpValidator.validateFirstname(firstname);
            if (error.equals(""))
                error = signUpValidator.validateLastname(lastname);
            if (error.equals(""))
                error = signUpValidator.validatePassword(password);
            if (error.equals(""))
                error = signUpValidator.validateEmail(email);
            if (error.equals(""))
                error = signUpValidator.validatePhoneNumber(phoneNumber);

            if (!error.equals("")) {
                logger.info(error);
                return new EnterResponse(false, error);
            } else {

                Chat savedMessageChat = new Chat(Config.getConfig("serverConfig").getProperty(Config.class,"messagingPage").getProperty("savedMessages"),
                        false);
                Connector.getInstance().save(savedMessageChat);

                User user = new User(username, firstname, lastname, bio, birthDate, email,
                        phoneNumber, password, publicData, lastSeenType, ImageLoader.DEFAULT_AVATAR_ID);
                Connector.getInstance().save(user);

                savedMessageChat.addUser(user.getId());
                Connector.getInstance().save(savedMessageChat);

                ChatState chatState = new ChatState(savedMessageChat.getId());
                Connector.getInstance().save(chatState);

                user.addChatState(chatState.getId());
                user.setLastSeen(null);
                Connector.getInstance().save(user);
                clientHandler.setUser(user);

                logger.info(String.format("user %s registered.", username));
                return new EnterResponse(true, "");
            }
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("serverConfig").getProperty(Config.class,"server").getProperty("databaseDisconnectError"));
        }
    }
}
