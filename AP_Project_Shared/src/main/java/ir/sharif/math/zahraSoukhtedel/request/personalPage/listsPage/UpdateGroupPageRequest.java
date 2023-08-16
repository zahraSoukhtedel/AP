package ir.sharif.math.zahraSoukhtedel.request.personalPage.listsPage;

import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.request.RequestVisitor;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import lombok.ToString;

@ToString
public class UpdateGroupPageRequest extends Request {
    private final Integer groupId;

    public UpdateGroupPageRequest(Integer groupId) {
        this.groupId = groupId;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.updateGroupPage(groupId);
    }
}
