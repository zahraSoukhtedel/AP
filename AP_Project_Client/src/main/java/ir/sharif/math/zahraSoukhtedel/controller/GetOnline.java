package ir.sharif.math.zahraSoukhtedel.controller;

import ir.sharif.math.zahraSoukhtedel.controller.network.SocketRequestSender;
import ir.sharif.math.zahraSoukhtedel.util.Config;

import java.net.Socket;

public class GetOnline {
    static public Socket checkConnection() {
        Config config = Config.getConfig("client");
        int port = config.getProperty(Integer.class, "port") != null ?
                config.getProperty(Integer.class, "port") : 8000;
        String host = config.getProperty(String.class, "host") != null ?
                config.getProperty(String.class, "host") : "";

        try {
            Socket socket = new Socket(host, port);
            return socket;
        } catch (Exception e) {
            return null;

        }
    }
}
