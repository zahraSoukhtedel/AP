package ir.sharif.math.zahraSoukhtedel.response.messagingPage.chatGroupPage;

import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.response.ResponseVisitor;
import lombok.Getter;

public class ChatGroupEditResponse extends Response {

    @Getter
    private final String error;

    public ChatGroupEditResponse(String error) {
        this.error = error;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.applyEditChatGroupResponse(error);
    }
}
