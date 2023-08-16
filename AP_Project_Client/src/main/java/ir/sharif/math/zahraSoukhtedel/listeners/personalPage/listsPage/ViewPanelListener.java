package ir.sharif.math.zahraSoukhtedel.listeners.personalPage.listsPage;

import ir.sharif.math.zahraSoukhtedel.controller.Client;
import ir.sharif.math.zahraSoukhtedel.models.events.SwitchToProfileType;
import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.request.profileView.SwitchToProfilePageRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ViewPanelListener {

    static private final Logger logger = LogManager.getLogger(ViewPanelListener.class);

    public void viewEventOccurred(Integer userToBeVisitedID) {
        Request request = new SwitchToProfilePageRequest(SwitchToProfileType.USER, userToBeVisitedID, "");
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
    }
}
