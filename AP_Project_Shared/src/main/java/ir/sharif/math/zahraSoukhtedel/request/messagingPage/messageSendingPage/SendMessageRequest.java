package ir.sharif.math.zahraSoukhtedel.request.messagingPage.messageSendingPage;

import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.request.RequestVisitor;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
public class SendMessageRequest extends Request {
    @Getter
    private final Integer messageId;
    @Getter
    private final List<Integer> chats;
    @Getter
    private final List<Integer> groups;

    public SendMessageRequest(Integer messageId, List<Integer> chats, List<Integer> groups) {
        this.messageId = messageId;
        this.chats = chats;
        this.groups = groups;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.sendMessage(messageId, chats, groups);
    }
}
