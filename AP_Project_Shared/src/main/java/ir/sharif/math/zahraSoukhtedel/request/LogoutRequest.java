package ir.sharif.math.zahraSoukhtedel.request;

import ir.sharif.math.zahraSoukhtedel.response.Response;
import lombok.Getter;
import lombok.ToString;

@ToString
public class LogoutRequest extends Request {

    @Getter
    private final boolean terminate;

    public LogoutRequest(boolean terminate) {
        this.terminate = terminate;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.logout(terminate);
    }
}
