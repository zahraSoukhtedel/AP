package ir.sharif.math.zahraSoukhtedel.controller.network;

import ir.sharif.math.zahraSoukhtedel.exceptions.ClientDisconnectException;
import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.response.Response;

public interface RequestSender {
    Response sendRequest(Request request) throws ClientDisconnectException;
    void close();
}
