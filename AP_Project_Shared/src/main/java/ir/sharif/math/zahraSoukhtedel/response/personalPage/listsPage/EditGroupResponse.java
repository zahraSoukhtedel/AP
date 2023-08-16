package ir.sharif.math.zahraSoukhtedel.response.personalPage.listsPage;

import ir.sharif.math.zahraSoukhtedel.response.ResponseVisitor;
import ir.sharif.math.zahraSoukhtedel.response.Response;

public class EditGroupResponse extends Response {
    private final String error;

    public EditGroupResponse(String error) {
        this.error = error;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.applyEditGroupResponse(error);
    }
}
