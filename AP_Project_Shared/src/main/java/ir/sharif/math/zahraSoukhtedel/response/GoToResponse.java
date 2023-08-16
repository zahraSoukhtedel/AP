package ir.sharif.math.zahraSoukhtedel.response;

public class GoToResponse extends Response {
    private final String pageName;
    private final String message;
    public GoToResponse(String pageName, String message) {
        this.pageName = pageName;
        this.message = message;
    }
    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.goTo(pageName, message);
    }
}
