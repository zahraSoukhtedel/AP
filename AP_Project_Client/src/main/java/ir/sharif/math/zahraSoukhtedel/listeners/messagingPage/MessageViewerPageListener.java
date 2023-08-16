package ir.sharif.math.zahraSoukhtedel.listeners.messagingPage;

import ir.sharif.math.zahraSoukhtedel.controller.Client;
import ir.sharif.math.zahraSoukhtedel.models.events.SwitchToProfileType;
import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.request.messagingPage.DeleteMessageRequest;
import ir.sharif.math.zahraSoukhtedel.request.messagingPage.ForwardMessageRequest;
import ir.sharif.math.zahraSoukhtedel.request.profileView.SwitchToProfilePageRequest;
import ir.sharif.math.zahraSoukhtedel.view.Page;
import ir.sharif.math.zahraSoukhtedel.view.ViewManager;
import ir.sharif.math.zahraSoukhtedel.view.messagingPage.EditMessageFXController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MessageViewerPageListener {

    static private final Logger logger = LogManager.getLogger(MessageViewerPageListener.class);

    public void checkProfile(Integer messageID) {
        Request request = new SwitchToProfilePageRequest(SwitchToProfileType.MESSAGE, messageID, "");
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
    }

    public void forward(Integer messageID) {
        Request request = new ForwardMessageRequest(messageID);
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
    }

    public void edit(Integer messageID) {
        if (Client.isOnline()){
            Page page = new Page("editMessagePage");
            EditMessageFXController editMessageFXController = (EditMessageFXController) page.getFxController();
            editMessageFXController.setMessageId(messageID);
            ViewManager.getInstance().setPage(page);
        }
    }

    public void delete(Integer messageID) {
        Request request = new DeleteMessageRequest(messageID);
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
    }
}
