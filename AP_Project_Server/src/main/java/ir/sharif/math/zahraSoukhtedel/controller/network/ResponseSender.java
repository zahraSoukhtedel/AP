package ir.sharif.math.zahraSoukhtedel.controller.network;

import ir.sharif.math.zahraSoukhtedel.exceptions.ClientDisconnectException;
import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.response.Response;

public interface ResponseSender {
    Request getRequest() throws ClientDisconnectException;

    void sendResponse(Response response) throws ClientDisconnectException;

    void close();
}
