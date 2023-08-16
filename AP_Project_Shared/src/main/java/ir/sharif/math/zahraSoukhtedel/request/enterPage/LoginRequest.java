package ir.sharif.math.zahraSoukhtedel.request.enterPage;

import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.request.RequestVisitor;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import lombok.ToString;

@ToString
public class LoginRequest extends Request {

    private final String username;
    private final String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.login(username, password);
    }
}
