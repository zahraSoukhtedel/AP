package ir.sharif.math.zahraSoukhtedel.listeners.enterPage;

import ir.sharif.math.zahraSoukhtedel.controller.Client;
import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.request.enterPage.LoginRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SignInFormListener {

    static private final Logger logger = LogManager.getLogger(SignInFormListener.class);

    public void login(String username, String password) {
        //*****************************************************************
        Client.getUserOffline().setUsername(username);
        Client.getUserOffline().setPassword(password);
        //*****************************************************************
        Request request = new LoginRequest(username, password);
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
    }
}
