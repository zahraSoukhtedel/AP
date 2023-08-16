package ir.sharif.math.zahraSoukhtedel.response;

public class ShowErrorResponse extends Response{
    private final String message;

    public ShowErrorResponse(String message) {
        this.message = message;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) { responseVisitor.showError(message); }
}
