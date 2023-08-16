package ir.sharif.math.zahraSoukhtedel.response;

public abstract class Response {
    public abstract void visit(ResponseVisitor responseVisitor);
}
