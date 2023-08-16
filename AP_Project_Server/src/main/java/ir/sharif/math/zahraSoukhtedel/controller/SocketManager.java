package ir.sharif.math.zahraSoukhtedel.controller;

import ir.sharif.math.zahraSoukhtedel.controller.bot.BotHandler;
import ir.sharif.math.zahraSoukhtedel.controller.network.ResponseSender;
import ir.sharif.math.zahraSoukhtedel.exceptions.DatabaseDisconnectException;
import ir.sharif.math.zahraSoukhtedel.controller.network.SocketResponseSender;
import ir.sharif.math.zahraSoukhtedel.database.Connector;
import ir.sharif.math.zahraSoukhtedel.util.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class SocketManager extends Thread {

    static private final Logger logger = LogManager.getLogger(SocketManager.class);

    private final ServerSocket serverSocket;
    private final List<ClientHandler> clientHandlers;
    private final BotHandler botHandler;

    public SocketManager() throws IOException, DatabaseDisconnectException {
        Config config = Config.getConfig("server");
        int port = config.getProperty(Integer.class, "port") != null ?
                config.getProperty(Integer.class, "port") : 8000;
        serverSocket = new ServerSocket(port);
        //to build connection to database
        Connector.getInstance();
        clientHandlers = Collections.synchronizedList(new ArrayList<>());
        botHandler = new BotHandler();
    }

    public void start() {
        new Thread(this::getOrders).start();
        accept();
    }

    private void accept() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                logger.info(String.format("socket %s is connected", socket));
                ResponseSender responseSender = new SocketResponseSender(socket);
                ClientHandler clientHandler = new ClientHandler(responseSender);
                clientHandlers.add(clientHandler);
                clientHandler.start();
            } catch (IOException ignore) {
            }
        }
    }

    private void getOrders() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("type add bot to add bot");
            System.out.println("type remove bots to remove bots");
            switch (scanner.nextLine().strip()) {
                case "add bot" :
                    try {
                        System.out.println("enter jar url");
                        String jarUrl = scanner.nextLine().strip();
                        botHandler.loadBot(jarUrl);
                        System.out.println("successfully added");
                        logger.info("new bot added");
                    } catch (Exception e) {
                        System.out.println("can't add the bot");
                        logger.warn("can't add the requested bot");
                    }
                    break;
                case "remove bots":
                    try {
                        botHandler.removeBots();
                        System.out.println("successfully removed bots");
                        logger.info("bots removed");
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("can't remove bots");
                        logger.warn("can't remove bots");
                    }
                    break;
                default :
                    System.out.println("unknown command");
            }
        }
    }


    public void removeClientHandler(SocketResponseSender socketResponseSender) {
        clientHandlers.removeIf(clientHandler -> clientHandler.getResponseSender().equals(socketResponseSender));
    }

}
