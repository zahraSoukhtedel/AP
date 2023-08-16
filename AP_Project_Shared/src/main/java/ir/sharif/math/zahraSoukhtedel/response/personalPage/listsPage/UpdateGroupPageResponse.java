package ir.sharif.math.zahraSoukhtedel.response.personalPage.listsPage;

import ir.sharif.math.zahraSoukhtedel.models.viewModels.ViewUser;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.response.ResponseVisitor;

import java.util.List;

public class UpdateGroupPageResponse extends Response {

    private final List<ViewUser> members;
    public UpdateGroupPageResponse(List<ViewUser> members) {
        this.members = members;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.updateGroupPage(members);
    }
}
