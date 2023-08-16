package ir.sharif.math.zahraSoukhtedel.request.messagingPage.chatGroupPage;

import ir.sharif.math.zahraSoukhtedel.models.events.ChatGroupEventType;
import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.request.RequestVisitor;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import lombok.Getter;
import lombok.ToString;

@ToString
public class ChatGroupEditRequest extends Request {
    @Getter
    private final ChatGroupEventType chatGroupEventType;
    @Getter
    private final String groupName;
    @Getter
    private final String username;

    public ChatGroupEditRequest(ChatGroupEventType chatGroupEventType, String groupName, String username) {
        this.chatGroupEventType = chatGroupEventType;
        this.groupName = groupName;
        this.username = username;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.chatGroupRequestHandle(chatGroupEventType, groupName, username);
    }
}
