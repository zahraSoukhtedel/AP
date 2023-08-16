package ir.sharif.math.zahraSoukhtedel.request.personalPage.listsPage;

import ir.sharif.math.zahraSoukhtedel.models.events.GroupPageEventType;
import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.request.RequestVisitor;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import lombok.ToString;

@ToString
public class EditGroupRequest extends Request {
    private final GroupPageEventType groupPageEventType;
    private final Integer group;
    private final String username;

    public EditGroupRequest(GroupPageEventType groupPageEventType, Integer group, String username) {
        this.groupPageEventType = groupPageEventType;
        this.group = group;
        this.username = username;
    }


    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.editGroup(groupPageEventType, group, username);
    }
}
