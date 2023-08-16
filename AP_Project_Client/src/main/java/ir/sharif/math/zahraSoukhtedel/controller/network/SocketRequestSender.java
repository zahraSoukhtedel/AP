package ir.sharif.math.zahraSoukhtedel.controller.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ir.sharif.math.zahraSoukhtedel.exceptions.ClientDisconnectException;
import ir.sharif.math.zahraSoukhtedel.gson.Deserializer;
import ir.sharif.math.zahraSoukhtedel.gson.Serializer;
import ir.sharif.math.zahraSoukhtedel.request.LogoutRequest;
import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.response.enterPage.EnterResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class SocketRequestSender implements RequestSender {
    static private final Logger logger = LogManager.getLogger(RequestSender.class);

    private final Serializer serializer;
    private final Deserializer deserializer;
    String token;
    private final Socket socket;
    private final PrintStream printStream;
    private final Scanner scanner;
    private final Gson gson;

    public SocketRequestSender(Socket socket) throws IOException {
        serializer = new Serializer<>();
        deserializer = new Deserializer<>();
        this.socket = socket;
        this.scanner = new Scanner(socket.getInputStream());
        this.printStream = new PrintStream(socket.getOutputStream());
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Response.class, deserializer)
                .registerTypeAdapter(Request.class, serializer)
                .create();
    }

    @Override
    public Response sendRequest(Request request) throws ClientDisconnectException {
        try {
            String requestString = gson.toJson(request, Request.class);
            printStream.println(requestString);
            String responseString = scanner.nextLine();
            Response response = gson.fromJson(responseString, Response.class);
            if (response instanceof EnterResponse && ((EnterResponse) response).getSuccess()) {
                token = deserializer.getToken();
                serializer.setToken(token);
            }
            if (request instanceof LogoutRequest) {
                token = null;
                serializer.setToken(null);
            }
            return response;
        } catch (Throwable throwable) {
            logger.warn("client disconnected from server");
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
