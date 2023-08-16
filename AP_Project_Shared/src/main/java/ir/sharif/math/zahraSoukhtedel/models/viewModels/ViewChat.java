package ir.sharif.math.zahraSoukhtedel.models.viewModels;

import lombok.Getter;

public class ViewChat {
    @Getter
    private final Integer chatId;
    @Getter
    private final String chatName;
    @Getter
    private final int unreadCount;

    public ViewChat(Integer chatId, String chatName, int unreadCount) {
        this.chatId = chatId;
        this.chatName = chatName;
        this.unreadCount = unreadCount;
    }

}
