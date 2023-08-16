package ir.sharif.math.zahraSoukhtedel.request;

import ir.sharif.math.zahraSoukhtedel.response.Response;
import lombok.ToString;

@ToString
public abstract class Request {
    public abstract Response visit(RequestVisitor requestVisitor);
}
