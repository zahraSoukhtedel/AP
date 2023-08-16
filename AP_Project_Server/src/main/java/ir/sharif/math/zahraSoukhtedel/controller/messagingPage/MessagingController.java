package ir.sharif.math.zahraSoukhtedel.controller.messagingPage;

import ir.sharif.math.zahraSoukhtedel.controller.ClientHandler;
import ir.sharif.math.zahraSoukhtedel.database.Connector;
import ir.sharif.math.zahraSoukhtedel.exceptions.DatabaseDisconnectException;
import ir.sharif.math.zahraSoukhtedel.models.Chat;
import ir.sharif.math.zahraSoukhtedel.models.ChatState;
import ir.sharif.math.zahraSoukhtedel.models.User;
import ir.sharif.math.zahraSoukhtedel.models.media.Message;
import ir.sharif.math.zahraSoukhtedel.models.media.Tweet;
import ir.sharif.math.zahraSoukhtedel.models.viewModels.ViewChat;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.response.ShowErrorResponse;
import ir.sharif.math.zahraSoukhtedel.response.messagingPage.UpdateMessagingPageResponse;
import ir.sharif.math.zahraSoukhtedel.models.viewModels.ViewMessage;
import ir.sharif.math.zahraSoukhtedel.util.Config;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MessagingController {

    private final ClientHandler clientHandler;
    private final MessagingValidator messagingValidator;

    public MessagingController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        messagingValidator = new MessagingValidator();
    }

    private String chatName(User user, Chat chat) throws DatabaseDisconnectException {
        if (chat.isGroup() || chat.getUsers().size() == 1)
            return chat.getChatName();
        else if (chat.getUsers().get(0).equals(user.getId()))
            return Connector.getInstance().fetch(User.class, chat.getUsers().get(1)).getUsername();
        else
            return Connector.getInstance().fetch(User.class, chat.getUsers().get(0)).getUsername();
    }

    private int unreadCount(User user, ChatState chatState) throws DatabaseDisconnectException {
        Chat chat = Connector.getInstance().fetch(Chat.class, chatState.getChat());
        int unreadMessages = 0;
        for (Integer messageID : chat.getMessages()) {
            Message message = Connector.getInstance().fetch(Message.class, messageID);
            if (message.getDateTime().isAfter(chatState.getLastCheck()) && !message.getWriter().equals(user.getId()) &&
                    !message.isDeleted())
                unreadMessages++;
        }
        return unreadMessages;
    }

    private List<ViewChat> getChatUpdate() throws DatabaseDisconnectException {
        User user = clientHandler.getUser();
        //update chats
        List<ViewChat> chats = new ArrayList<>();
        List<ChatState> validatedChatStates = messagingValidator.getValidatedChatStates(user);
        validatedChatStates.sort((chatState1, chatState2) -> {
            int unreadCount1 = 0;
            int unreadCount2 = 0;
            try {
                unreadCount1 = unreadCount(user, chatState1);
                unreadCount2 = unreadCount(user, chatState2);
            } catch (DatabaseDisconnectException e) {
                e.printStackTrace();
            }
            return Integer.compare(unreadCount2, unreadCount1);
        }); //sort by unread messages.
        for (ChatState chatState : validatedChatStates) {
            Chat chat = Connector.getInstance().fetch(Chat.class, chatState.getChat());
            chats.add(new ViewChat(chatState.getChat(), chatName(user, chat), unreadCount(user, chatState)));
        }
        return chats;
    }

    public void updateChatLastCheck(User user, Integer chatId) throws DatabaseDisconnectException {
        for (Integer chatStateId : user.getChatStates()) {
            ChatState chatState = Connector.getInstance().fetch(ChatState.class, chatStateId);
            if (chatState.getChat().equals(chatId)) {
                chatState.setLastCheck(LocalDateTime.now());
                Connector.getInstance().save(chatState);
            }
        }
        Connector.getInstance().save(user);
    }



    private List<ViewMessage> getMessageUpdate(Integer chatId) throws DatabaseDisconnectException{
        User user = clientHandler.getUser();
        Chat chat = Connector.getInstance().fetch(Chat.class, chatId);
        updateChatLastCheck(user, chatId);

        List<Message> validatedMessages = messagingValidator.getValidatedMessages(chat.getMessages());
        List<ViewMessage> messages = new ArrayList<>();
        Message.sortByDateTime(validatedMessages);
        for (Message message : validatedMessages) {
            Integer messageId = message.getId();
            String messageContent = message.getContent();
            LocalDateTime messageDateTime = message.getDateTime();
            String messageState = getMessageState(user, chat, message);

            String writerUsername;
            if (message.getMainMedia() == null)
                writerUsername = Connector.getInstance().fetch(User.class, message.getWriter()).getUsername();
            else if (message.isForwardedTweet()) {
                Tweet mainTweet = Connector.getInstance().fetch(Tweet.class, message.getMainMedia());
                String tweetWriter = Connector.getInstance().fetch(User.class, mainTweet.getWriter()).getUsername();
                writerUsername = String.format("%s forwarded a tweet from %s",
                        Connector.getInstance().fetch(User.class, message.getWriter()).getUsername(), tweetWriter);
            } else {
                Message mainMessage = Connector.getInstance().fetch(Message.class, message.getMainMedia());
                String messageWriter = Connector.getInstance().fetch(User.class, mainMessage.getWriter()).getUsername();
                writerUsername = String.format("%s forwarded a message from %s",
                        Connector.getInstance().fetch(User.class, message.getWriter()).getUsername(), messageWriter);
            }
            //################################################
            if (messageDateTime.compareTo(LocalDateTime.now()) <= 0){
                messages.add(new ViewMessage(messageId, messageContent, messageDateTime, messageState, writerUsername));
            }
            //###################################################
        }
        return messages;
    }

    private String getMessageState(User user, Chat chat, Message message) throws DatabaseDisconnectException {
        if(chat.isGroup() || chat.getUsers().size() == 1)
            return null;
        User otherUser;
        if (chat.getUsers().get(0).equals(user.getId()))
            otherUser = Connector.getInstance().fetch(User.class, chat.getUsers().get(1));
        else
            otherUser = Connector.getInstance().fetch(User.class, chat.getUsers().get(0));
        if(message.getWriter().equals(otherUser.getId()))
            return null;
        ChatState chatState = null;
        for (Integer chatStateId : otherUser.getChatStates()) {
            ChatState currentChatState = Connector.getInstance().fetch(ChatState.class, chatStateId);
            if(currentChatState.getChat().equals(chat.getId()))
                chatState = currentChatState;
        }
        if(otherUser.getLastSeen() != null && message.getDateTime().isAfter(otherUser.getLastSeen()))
            return "2";

        if(message.getDateTime().isAfter(chatState.getLastCheck()))
            return "3";
        return "4";
    }

    public Response getUpdate(Integer chatId, boolean isChanged) {
        try {
            //update chats
            List<ViewChat> chats = getChatUpdate();
            //update chat messages
            if(chatId == null)
                return new UpdateMessagingPageResponse(isChanged, null, null, chats, null);
            List<ViewMessage> messages = getMessageUpdate(chatId);
            //get Chat name
            Chat chat = Connector.getInstance().fetch(Chat.class, chatId);
            User user = clientHandler.getUser();
            return new UpdateMessagingPageResponse(isChanged, chatId, chatName(user, chat), chats, messages);

        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("server").getProperty("databaseDisconnectError"));
        }
    }
}
