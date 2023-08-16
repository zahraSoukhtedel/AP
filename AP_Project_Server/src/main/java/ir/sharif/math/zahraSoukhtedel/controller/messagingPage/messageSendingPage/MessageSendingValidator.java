package ir.sharif.math.zahraSoukhtedel.controller.messagingPage.messageSendingPage;

import ir.sharif.math.zahraSoukhtedel.models.User;
import ir.sharif.math.zahraSoukhtedel.util.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MessageSendingValidator {

    static private final Logger logger = LogManager.getLogger(MessageSendingValidator.class);

    String SendMessageError(User writer, User receiver) {

        Config errorsConfig = Config.getConfig("serverConfig").getProperty(Config.class,"messageSendingPage");

        if(writer.equals(receiver)) {
            logger.info(String.format("user %s wants to send an invalid message.", writer.getUsername()));
            return errorsConfig.getProperty("messageToSelfError");
        }
        if(!receiver.isActive()) {
            logger.info(String.format("user %s wants to send an invalid message.", writer.getUsername()));
            return errorsConfig.getProperty("messageToDeactivatedUserError");
        }
        if (!writer.getFollowings().contains(receiver.getId()) && !receiver.getFollowings().contains(writer.getId())) {
            logger.info(String.format("user %s wants to send an invalid message.", writer.getUsername()));
            return errorsConfig.getProperty("invalidFollowError");
        }

        if (writer.getBlockList().contains(receiver.getId())) {
            logger.info(String.format("user %s wants to send an invalid message.", writer.getUsername()));
            return errorsConfig.getProperty("youBlockedReceiverError");
        }
        if (receiver.getBlockList().contains(writer.getId())) {
            logger.info(String.format("user %s wants to send an invalid message.", writer.getUsername()));
            return errorsConfig.getProperty("receiverBlockedYouError");
        }
        return "";
    }
}
