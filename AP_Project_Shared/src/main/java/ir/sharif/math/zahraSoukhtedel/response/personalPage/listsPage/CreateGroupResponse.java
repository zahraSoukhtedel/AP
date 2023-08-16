package ir.sharif.math.zahraSoukhtedel.response.personalPage.listsPage;

import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.response.ResponseVisitor;

public class CreateGroupResponse extends Response {
    private final String error;

    public CreateGroupResponse(String error) {
        this.error = error;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.applyCreateGroupResponse(error);
    }
}
