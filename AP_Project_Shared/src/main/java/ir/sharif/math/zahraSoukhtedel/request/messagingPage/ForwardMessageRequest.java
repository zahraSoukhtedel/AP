package ir.sharif.math.zahraSoukhtedel.request.messagingPage;

import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.request.RequestVisitor;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import lombok.ToString;

@ToString
public class ForwardMessageRequest extends Request {
    private final Integer messageId;

    public ForwardMessageRequest(Integer messageId) {
        this.messageId = messageId;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.forwardMessage(messageId);
    }
}
