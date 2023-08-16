package ir.sharif.math.zahraSoukhtedel.request.personalPage.notificationsPage;

import ir.sharif.math.zahraSoukhtedel.models.events.RequestAnswerType;
import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.request.RequestVisitor;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import lombok.ToString;

@ToString
public class FollowRequest extends Request {
    private final RequestAnswerType requestAnswerType;
    private final Integer requesterID;

    public FollowRequest(RequestAnswerType requestAnswerType, Integer requesterID) {
        this.requestAnswerType = requestAnswerType;
        this.requesterID = requesterID;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.followRequestHandle(requestAnswerType, requesterID);
    }
}
