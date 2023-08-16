package ir.sharif.math.zahraSoukhtedel.response.messagingPage.messageSendingPage;

import ir.sharif.math.zahraSoukhtedel.models.viewModels.ViewChat;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.response.ResponseVisitor;
import ir.sharif.math.zahraSoukhtedel.models.viewModels.ViewGroup;
import lombok.Getter;

import java.util.List;

public class NewMessageResponse extends Response {

    @Getter
    private final String error;
    @Getter
    private final Integer messageId;
    @Getter
    private final List<ViewGroup> groups;
    @Getter
    private final List<ViewChat> chats;

    public NewMessageResponse(String error, Integer messageId, List<ViewGroup> groups, List<ViewChat> chats) {
        this.error = error;
        this.messageId = messageId;
        this.groups = groups;
        this.chats = chats;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.applyNewMessageResponse(error, messageId, groups, chats);
    }
}
