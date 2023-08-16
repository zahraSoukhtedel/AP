package ir.sharif.math.zahraSoukhtedel.listeners.messagingPage.messageSendigPage;

import ir.sharif.math.zahraSoukhtedel.view.messagingPage.messageSendingPage.ChatSelectingFXController;

public class ChatPanelToChatPageListener {
    ChatSelectingFXController chatSelectingFXController;

    public ChatPanelToChatPageListener(ChatSelectingFXController chatSelectingFXController) {
        this.chatSelectingFXController = chatSelectingFXController;
    }

    public void switchState(Integer chat) {
        if(chatSelectingFXController.getSelectedChats().contains(chat))
            chatSelectingFXController.removeChat(chat);
        else
            chatSelectingFXController.addChat(chat);
    }
}
