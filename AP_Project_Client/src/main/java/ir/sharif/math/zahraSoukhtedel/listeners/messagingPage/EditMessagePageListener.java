package ir.sharif.math.zahraSoukhtedel.listeners.messagingPage;

import ir.sharif.math.zahraSoukhtedel.controller.Client;
import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.request.messagingPage.EditMessageRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EditMessagePageListener {
    static private final Logger logger = LogManager.getLogger(EditMessagePageListener.class);

    public void edit(Integer messageId, String messageContent) {
        Request request = new EditMessageRequest(messageId, messageContent);
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
    }
}
