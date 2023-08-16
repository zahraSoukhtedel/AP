package ir.sharif.math.zahraSoukhtedel.response.messagingPage;

import ir.sharif.math.zahraSoukhtedel.models.viewModels.ViewChat;
import ir.sharif.math.zahraSoukhtedel.models.viewModels.ViewMessage;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.response.ResponseVisitor;

import java.util.List;

public class UpdateMessagingPageResponse extends Response {
    private final boolean isChanged;
    private final Integer chatId;
    private final String chatName;
    private final List<ViewChat> chats;
    private final List<ViewMessage> messages;

    public UpdateMessagingPageResponse(boolean isChanged, Integer chatId, String chatName, List<ViewChat> chats, List<ViewMessage> messages) {
        this.isChanged = isChanged;
        this.chatId = chatId;
        this.chatName = chatName;
        this.chats = chats;
        this.messages = messages;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.updateMessagingPage(isChanged, chatId, chatName, chats, messages);
    }
}
