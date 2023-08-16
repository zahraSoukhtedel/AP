package ir.sharif.math.zahraSoukhtedel.response;

public class BackResponse extends Response{
    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.back();
    }
}
