package ir.sharif.math.zahraSoukhtedel.listeners.messagingPage.messageSendigPage;

import ir.sharif.math.zahraSoukhtedel.controller.Client;
import ir.sharif.math.zahraSoukhtedel.model.ChatOffline;
import ir.sharif.math.zahraSoukhtedel.model.GroupOffline;
import ir.sharif.math.zahraSoukhtedel.model.MessageOffline;
import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.request.messagingPage.messageSendingPage.SendMessageRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ChatSelectingPageListener {

    static private final Logger logger = LogManager.getLogger(ChatSelectingPageListener.class);

    public void send(Integer messageID, List<Integer> selectedChats, List<Integer> selectedGroups) {
        Request request = new SendMessageRequest(messageID, selectedChats, selectedGroups);
        logger.info(String.format("client requested %s", request));
        if (!Client.isOnline()){
            MessageOffline messageOffline = Client.getUserOffline().getNewMessages().get(messageID-10000);
            for (ChatOffline chatOffline : Client.getUserOffline().getLoadedChats()){
                if (selectedChats.contains(chatOffline.getViewChat().getChatId())){
                    chatOffline.getUnSendMessages().add(messageOffline);
                }
            }
            for (GroupOffline groupOffline : Client.getUserOffline().getLoadedGroups()){
                if (selectedGroups.contains(groupOffline.getViewGroup().getGroupId())){
                    groupOffline.getUnSendMessages().add(messageOffline);
                }
            }
            Client.getMessageRequests().get(messageID).add(request);
        }
        else {

            Client.getClient().addRequest(request);
        }
    }
}
