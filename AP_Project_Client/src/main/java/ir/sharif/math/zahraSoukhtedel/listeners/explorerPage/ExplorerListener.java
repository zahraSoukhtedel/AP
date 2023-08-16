package ir.sharif.math.zahraSoukhtedel.listeners.explorerPage;

import ir.sharif.math.zahraSoukhtedel.controller.Client;
import ir.sharif.math.zahraSoukhtedel.models.events.SwitchToProfileType;
import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.request.profileView.SwitchToProfilePageRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExplorerListener {

    static private final Logger logger = LogManager.getLogger(ExplorerListener.class);

    public void search(String username) {
        Request request = new SwitchToProfilePageRequest(SwitchToProfileType.USERNAME, null, username);
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
    }

}
