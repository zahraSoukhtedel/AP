package ir.sharif.math.zahraSoukhtedel.listeners.messagingPage;


import ir.sharif.math.zahraSoukhtedel.controller.Client;
import ir.sharif.math.zahraSoukhtedel.controller.messagingPage.MessagingController;
import ir.sharif.math.zahraSoukhtedel.model.ChatOffline;
import ir.sharif.math.zahraSoukhtedel.model.MessageOffline;
import ir.sharif.math.zahraSoukhtedel.models.viewModels.ViewChat;
import ir.sharif.math.zahraSoukhtedel.models.viewModels.ViewMessage;
import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.request.messagingPage.UpdateMessagingPageRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ChatPanelToMessagingPageListener {

    static private final Logger logger = LogManager.getLogger(ChatPanelToMessagingPageListener.class);

    public void viewChat(Integer chatID) {
        if (Client.isOnline()){
            Request request = new UpdateMessagingPageRequest(chatID, true);
            logger.info(String.format("client requested %s", request));
            Client.getClient().addRequest(request);
        }
        else {
            MessagingController messagingController = new MessagingController();
            String chatName = null;
            List<ViewChat> chats = new ArrayList<>();
            List<ViewMessage> messages = new ArrayList<>();
            for (ChatOffline chatOffline :Client.getUserOffline().getLoadedChats()){
                chats.add(chatOffline.getViewChat());
                if (chatOffline.getViewChat().getChatId().equals(chatID)){

                    chatName = chatOffline.getViewChat().getChatName();
                    for (MessageOffline messageOffline : chatOffline.getLoadedMessages()){
                        messages.add(messageOffline.getViewMessage());
                    }

                    for (MessageOffline messageOffline : chatOffline.getUnSendMessages()){
                        messages.add(messageOffline.getViewMessage());
                    }

                }
            }
            messagingController.refresh(true,chatID, chatName, chats, messages);
        }
    }
}
