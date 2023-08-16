package ir.sharif.math.zahraSoukhtedel.response;

import lombok.Getter;

public class LogoutResponse extends Response{

    @Getter
    private final boolean terminate;

    public LogoutResponse(boolean terminate) {
        this.terminate = terminate;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.logout(terminate);
    }
}
