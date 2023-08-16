package ir.sharif.math.zahraSoukhtedel.model;

import ir.sharif.math.zahraSoukhtedel.models.viewModels.ViewChat;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class ChatOffline {
    @Getter @Setter
    private ViewChat viewChat;
    @Getter @Setter
    private List<MessageOffline> unSendMessages;
    @Getter @Setter
    private List<MessageOffline> loadedMessages;

    public ChatOffline(ViewChat viewChat){
        this.viewChat = viewChat;
        unSendMessages = new ArrayList<>();
        loadedMessages = new ArrayList<>();
    }
}