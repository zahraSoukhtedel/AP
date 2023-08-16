package ir.sharif.math.zahraSoukhtedel.request.profileView;

import ir.sharif.math.zahraSoukhtedel.models.events.SwitchToProfileType;
import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.request.RequestVisitor;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import lombok.ToString;

@ToString
public class SwitchToProfilePageRequest extends Request {

    private final SwitchToProfileType switchToProfileType;
    private final Integer id;
    private final String username;

    public SwitchToProfilePageRequest(SwitchToProfileType switchToProfileType, Integer id, String username) {
        this.switchToProfileType = switchToProfileType;
        this.id = id;
        this.username = username;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.switchToProfilePage(switchToProfileType, id, username);
    }
}
