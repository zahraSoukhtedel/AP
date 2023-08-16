package ir.sharif.math.zahraSoukhtedel.controller;

import ir.sharif.math.zahraSoukhtedel.database.Connector;
import ir.sharif.math.zahraSoukhtedel.exceptions.DatabaseDisconnectException;
import ir.sharif.math.zahraSoukhtedel.util.Config;

public class DataValidator {

    private final Config errorsConfig = Config.getConfig("serverConfig").getProperty(Config.class,"signUpPage");

    public String validateUsername(String username) throws DatabaseDisconnectException {
        if(username.equals(""))
            return errorsConfig.getProperty("emptyUsernameError");
        if(Connector.getInstance().getUserByUsername(username) != null)
            return errorsConfig.getProperty("duplicateUsernameError");
        return "";
    }

    public String validateFirstname(String firstname) {
        if(firstname.equals(""))
            return errorsConfig.getProperty("emptyFirstnameError");
        return "";
    }

    public String validateLastname(String lastname) {
        if(lastname.equals(""))
            return errorsConfig.getProperty("emptyLastnameError");
        return "";
    }

    public String validatePassword(String password) {
        if(password.equals(""))
            return errorsConfig.getProperty("emptyPasswordError");
        return "";
    }

    public String validateEmail(String email) throws DatabaseDisconnectException {
        if(email.equals(""))
            return errorsConfig.getProperty("emptyEmailError");
        if(Connector.getInstance().getUserByEmail(email) != null)
            return errorsConfig.getProperty("duplicateEmailError");
        return "";
    }

    public String validatePhoneNumber(String phoneNumber) throws DatabaseDisconnectException {
        if(!phoneNumber.equals("") && Connector.getInstance().getUserByPhoneNumber(phoneNumber) != null)
            return errorsConfig.getProperty("duplicatePhoneNumberError");
        return "";
    }
}
