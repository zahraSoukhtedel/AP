package ir.sharif.math.zahraSoukhtedel.request.messagingPage;

import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.request.RequestVisitor;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import lombok.ToString;

@ToString
public class EditMessageRequest extends Request {
    private final Integer messageId;
    private final String messageContent;

    public EditMessageRequest(Integer messageId, String messageContent) {
        this.messageId = messageId;
        this.messageContent = messageContent;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.editMessage(messageId, messageContent);
    }
}
