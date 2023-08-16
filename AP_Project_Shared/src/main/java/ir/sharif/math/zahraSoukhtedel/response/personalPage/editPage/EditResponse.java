package ir.sharif.math.zahraSoukhtedel.response.personalPage.editPage;

import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.response.ResponseVisitor;

public class EditResponse extends Response {

    private final String error;

    public EditResponse(String error) {
        this.error = error;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.applyEditResponse(error);
    }
}
