package ir.sharif.math.zahraSoukhtedel.listeners.personalPage.listsPage;

import ir.sharif.math.zahraSoukhtedel.controller.Client;
import ir.sharif.math.zahraSoukhtedel.models.events.GroupPageEventType;
import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.request.personalPage.listsPage.EditGroupRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GroupPageListener {

    static private final Logger logger = LogManager.getLogger(GroupPageListener.class);

    public void removeGroup(Integer group) {
        Request request = new EditGroupRequest(GroupPageEventType.REMOVE_GROUP, group, "");
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
    }

    public void removeUser(Integer group, String username) {
        Request request = new EditGroupRequest(GroupPageEventType.REMOVE_USER, group, username);
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
    }

    public void addUser(Integer group, String username) {
        Request request = new EditGroupRequest(GroupPageEventType.ADD_USER, group, username);
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
    }
}
