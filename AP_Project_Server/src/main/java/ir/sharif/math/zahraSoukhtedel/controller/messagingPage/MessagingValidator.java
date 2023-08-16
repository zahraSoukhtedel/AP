package ir.sharif.math.zahraSoukhtedel.controller.messagingPage;

import ir.sharif.math.zahraSoukhtedel.database.Connector;
import ir.sharif.math.zahraSoukhtedel.exceptions.DatabaseDisconnectException;
import ir.sharif.math.zahraSoukhtedel.models.Chat;
import ir.sharif.math.zahraSoukhtedel.models.ChatState;
import ir.sharif.math.zahraSoukhtedel.models.User;
import ir.sharif.math.zahraSoukhtedel.models.media.Message;

import java.util.ArrayList;
import java.util.List;

public class MessagingValidator {
    public List<ChatState> getValidatedChatStates(User user) throws DatabaseDisconnectException {
        List<ChatState> validatedChatStates = new ArrayList<>();
        for (Integer chatStateId : user.getChatStates()) {
            ChatState chatState = Connector.getInstance().fetch(ChatState.class, chatStateId);
            Integer chatID = chatState.getChat();
            Chat chat = Connector.getInstance().fetch(Chat.class, chatID);
            if (chat.isGroup() || chat.getUsers().size() != 2)
                validatedChatStates.add(chatState);
            else {
                Integer otherUser = chat.getUsers().get(0);
                if (otherUser.equals(user.getId()))
                    otherUser = chat.getUsers().get(1);
                if (Connector.getInstance().fetch(User.class, otherUser).isActive())
                    validatedChatStates.add(chatState);
            }
        }
        return validatedChatStates;
    }

    public List<Message> getValidatedMessages(List<Integer> messages) throws DatabaseDisconnectException {
        List<Message> validatedMessages = new ArrayList<>();
        for (Integer messageID : messages) {
            Message message = Connector.getInstance().fetch(Message.class, messageID);
            if (message.isDeleted())
                continue; //message deleted
            if (!message.isForwardedTweet() && message.getMainMedia() != null &&
                    Connector.getInstance().fetch(Message.class, message.getMainMedia()).isDeleted())
                continue; //parent message deleted
            if (!Connector.getInstance().fetch(User.class, message.getWriter()).isActive())
                continue; //writer not active
            validatedMessages.add(message);
        }
        return validatedMessages;
    }
}
