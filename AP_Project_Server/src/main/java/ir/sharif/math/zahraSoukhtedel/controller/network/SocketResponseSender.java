package ir.sharif.math.zahraSoukhtedel.controller.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ir.sharif.math.zahraSoukhtedel.exceptions.ClientDisconnectException;
import ir.sharif.math.zahraSoukhtedel.gson.Deserializer;
import ir.sharif.math.zahraSoukhtedel.gson.Serializer;
import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.response.LogoutResponse;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.response.enterPage.EnterResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class SocketResponseSender implements ResponseSender {
    static private final Logger logger = LogManager.getLogger(SocketResponseSender.class);
    private final Serializer serializer;
    private final Deserializer deserializer;
    private final Socket socket;
    private final PrintStream printStream;
    private final Scanner scanner;
    private final Gson gson;
    private String token;

    public SocketResponseSender(Socket socket) throws IOException {
        this.serializer = new Serializer<>();
        this.deserializer = new Deserializer<>();
        this.socket = socket;
        this.scanner = new Scanner(socket.getInputStream());
        this.printStream = new PrintStream(socket.getOutputStream());
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Request.class, deserializer)
                .registerTypeAdapter(Response.class, serializer)
                .create();
    }

    @Override
    public Request getRequest() throws ClientDisconnectException {
        try {
            String requestString = scanner.nextLine();
            Request request = gson.fromJson(requestString, Request.class);
            String curToken = deserializer.getToken();
            if (token != null && !token.equals(curToken))
                try {
                    throw new Exception("wrong token");
                } catch (Exception e) {
                    logger.warn(String.format("socket %s is using wrong token to send request", socket));
                    e.printStackTrace();
                }
            return request;
        } catch (Throwable throwable) {
            logger.warn(String.format("socket %s is disconnected", socket));
            throw new ClientDisconnectException(throwable);
        }
    }

    @Override
    public void sendResponse(Response response) throws ClientDisconnectException {
        try {
            if (response instanceof EnterResponse && ((EnterResponse) response).getSuccess()) {
                token = TokenGenerator.getInstance().generateNewToken();
                serializer.setToken(token);
            }
            if (response instanceof LogoutResponse) {
                token = null;
                serializer.setToken(null);
            }
            printStream.println(gson.toJson(response, Response.class));
        } catch (Throwable throwable) {
            logger.warn(String.format("socket %s is disconnected", socket));
            throw new ClientDisconnectException(throwable);
        }
    }

    @Override
    public void close() {
        try {
            printStream.close();
            scanner.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
