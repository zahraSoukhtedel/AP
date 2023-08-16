package ir.sharif.math.zahraSoukhtedel.request;

import ir.sharif.math.zahraSoukhtedel.response.Response;
import lombok.ToString;

@ToString
public class DeleteAccountRequest extends Request {

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.deleteAccount();
    }
}