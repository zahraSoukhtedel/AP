package ir.sharif.math.zahraSoukhtedel.response.messagingPage;

import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.response.ResponseVisitor;

public class EditMessageResponse extends Response {
    private final String error;

    public EditMessageResponse(String error) {
        this.error = error;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.applyEditMessageResponse(error);
    }
}
