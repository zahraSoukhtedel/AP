package ir.sharif.math.zahraSoukhtedel.controller.messagingPage;

import ir.sharif.math.zahraSoukhtedel.models.media.Message;
import ir.sharif.math.zahraSoukhtedel.controller.ClientHandler;
import ir.sharif.math.zahraSoukhtedel.database.Connector;
import ir.sharif.math.zahraSoukhtedel.database.ImageLoader;
import ir.sharif.math.zahraSoukhtedel.exceptions.DatabaseDisconnectException;
import ir.sharif.math.zahraSoukhtedel.models.User;
import ir.sharif.math.zahraSoukhtedel.models.media.Tweet;
import ir.sharif.math.zahraSoukhtedel.response.BackResponse;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.response.ShowErrorResponse;
import ir.sharif.math.zahraSoukhtedel.response.messagingPage.EditMessageResponse;
import ir.sharif.math.zahraSoukhtedel.response.messagingPage.UpdateMessageViewerResponse;
import ir.sharif.math.zahraSoukhtedel.util.Config;
import ir.sharif.math.zahraSoukhtedel.util.ImageUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;

public class MessageViewerController {
    private final ClientHandler clientHandler;
    static private final Logger logger = LogManager.getLogger(MessageViewerController.class);

    public MessageViewerController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public Response getUpdate(Integer messageId) {
        try {
            User user = clientHandler.getUser();
            Message message = Connector.getInstance().fetch(Message.class, messageId);

            boolean deactivated = !message.getWriter().equals(user.getId());

            ImageLoader imageLoader = new ImageLoader();
            BufferedImage bufferedImage = imageLoader.getByID(message.getImage());

            ImageUtils imageUtils = new ImageUtils();
            String messageImage;
            try {
                messageImage = imageUtils.toString(bufferedImage, "png");
            } catch (IOException e) {
                logger.warn("can't convert buffered image to byte array");
                e.printStackTrace();
                return null;
            }

            String messageContent = message.getContent();
            LocalDateTime messageDateTime = message.getDateTime();

            String writerUsername;
            if (message.getMainMedia() == null)
                writerUsername = Connector.getInstance().fetch(User.class, message.getWriter()).getUsername();
            else if (message.isForwardedTweet()) {
                Tweet mainTweet = Connector.getInstance().fetch(Tweet.class, message.getMainMedia());
                String tweetWriter = Connector.getInstance().fetch(User.class, mainTweet.getWriter()).getUsername();
                writerUsername = String.format("%s forwarded a tweet from %s",
                        Connector.getInstance().fetch(User.class, message.getWriter()).getUsername(), tweetWriter);
            } else {
                Message mainMessage = Connector.getInstance().fetch(Message.class, message.getMainMedia());
                String messageWriter = Connector.getInstance().fetch(User.class, mainMessage.getWriter()).getUsername();
                writerUsername = String.format("%s forwarded a message from %s",
                        Connector.getInstance().fetch(User.class, message.getWriter()).getUsername(), messageWriter);
            }
            return new UpdateMessageViewerResponse(messageId,deactivated, messageImage, messageContent, messageDateTime, writerUsername);

        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("serverConfig").getProperty(Config.class,"server").getProperty("databaseDisconnectError"));
        }
    }

    public Response deleteMessage(Integer messageID) {
        try {
            Message message = Connector.getInstance().fetch(Message.class, messageID);
            message.setDeleted(true);
            Connector.getInstance().save(message);
            return new BackResponse();
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("serverConfig").getProperty(Config.class,"server").getProperty("databaseDisconnectError"));
        }
    }

    public Response editMessage(Integer messageID, String messageContent) {
        try {
            Message message = Connector.getInstance().fetch(Message.class, messageID);
            if (message.getMainMedia() == null) {
                message.setContent(messageContent);
                Connector.getInstance().save(message);
                logger.info(String.format("message %s is edited.", message.getId()));
                return new BackResponse();
            } else {
                logger.info(String.format("user wants to edit message %s which is forwarded", message.getId()));
                return new EditMessageResponse(
                        Config.getConfig("serverConfig").getProperty(Config.class,"messageViewerPage").getProperty("editForwardedMessageError"));
            }
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("serverConfig").getProperty(Config.class,"server").getProperty("databaseDisconnectError"));
        }
    }
}
