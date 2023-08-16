package ir.sharif.math.zahraSoukhtedel.request.personalPage.listsPage;

import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.request.RequestVisitor;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import lombok.ToString;

@ToString
public class CreateGroupRequest extends Request {

    private final String groupName;

    public CreateGroupRequest(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.createGroup(groupName);
    }
}
