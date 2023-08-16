package ir.sharif.math.zahraSoukhtedel.response.enterPage;

import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.response.ResponseVisitor;

public class EnterResponse extends Response {
    private final boolean success;
    private final String message;

    public EnterResponse(boolean success, String message) {
        this.message = message;
        this.success = success;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.enter(success, message);
    }

    public boolean getSuccess() { return success; }
}
