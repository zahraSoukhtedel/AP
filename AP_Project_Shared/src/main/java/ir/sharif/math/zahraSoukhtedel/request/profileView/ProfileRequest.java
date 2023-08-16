package ir.sharif.math.zahraSoukhtedel.request.profileView;

import ir.sharif.math.zahraSoukhtedel.models.events.ProfilePageEventType;
import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.request.RequestVisitor;
import lombok.ToString;

@ToString
public class ProfileRequest extends Request {
    private final ProfilePageEventType profilePageEventType;
    private final Integer userToBeVisited;

    public ProfileRequest(ProfilePageEventType profilePageEventType, Integer userToBeVisited) {
        this.profilePageEventType = profilePageEventType;
        this.userToBeVisited = userToBeVisited;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.profileHandle(profilePageEventType, userToBeVisited);
    }
}
