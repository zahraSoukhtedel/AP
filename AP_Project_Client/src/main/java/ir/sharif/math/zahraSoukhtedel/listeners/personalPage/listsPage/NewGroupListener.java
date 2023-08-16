package ir.sharif.math.zahraSoukhtedel.listeners.personalPage.listsPage;

import ir.sharif.math.zahraSoukhtedel.controller.Client;
import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.request.personalPage.listsPage.CreateGroupRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NewGroupListener {

    static private final Logger logger = LogManager.getLogger(NewGroupListener.class);

    public void eventOccurred(String groupName) {
        Request request = new CreateGroupRequest(groupName);
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
    }
}
