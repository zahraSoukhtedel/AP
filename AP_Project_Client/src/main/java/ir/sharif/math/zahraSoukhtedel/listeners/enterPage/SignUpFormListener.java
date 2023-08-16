package ir.sharif.math.zahraSoukhtedel.listeners.enterPage;

import ir.sharif.math.zahraSoukhtedel.controller.Client;
import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.request.enterPage.RegisterRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

public class SignUpFormListener {

    static private final Logger logger = LogManager.getLogger(SignUpFormListener.class);

    public void register(String username, String firstname, String lastname, String bio,
                         LocalDate birthdate, String email, String phoneNumber, String password,
                         boolean publicData, String lastSeen) {
        //*****************************************************************
        Client.getUserOffline().setUsername(username);
        Client.getUserOffline().setPassword(password);
        //*****************************************************************
        Request request = new RegisterRequest(username, firstname, lastname, bio, birthdate, email,
                phoneNumber, password, publicData, lastSeen);
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
    }
}
