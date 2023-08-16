package ir.sharif.math.zahraSoukhtedel.controller.messagingPage.messageSendingPage;

import ir.sharif.math.zahraSoukhtedel.models.media.Message;
import ir.sharif.math.zahraSoukhtedel.controller.ClientHandler;
import ir.sharif.math.zahraSoukhtedel.controller.messagingPage.MessagingValidator;
import ir.sharif.math.zahraSoukhtedel.database.Connector;
import ir.sharif.math.zahraSoukhtedel.database.ImageLoader;
import ir.sharif.math.zahraSoukhtedel.exceptions.DatabaseDisconnectException;
import ir.sharif.math.zahraSoukhtedel.models.Chat;
import ir.sharif.math.zahraSoukhtedel.models.ChatState;
import ir.sharif.math.zahraSoukhtedel.models.Group;
import ir.sharif.math.zahraSoukhtedel.models.User;
import ir.sharif.math.zahraSoukhtedel.models.media.Tweet;
import ir.sharif.math.zahraSoukhtedel.models.viewModels.ViewChat;
import ir.sharif.math.zahraSoukhtedel.models.viewModels.ViewGroup;
import ir.sharif.math.zahraSoukhtedel.response.BackResponse;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.response.ShowErrorResponse;
import ir.sharif.math.zahraSoukhtedel.response.messagingPage.messageSendingPage.NewMessageResponse;
import ir.sharif.math.zahraSoukhtedel.response.tweets.TweetActionResponse;
import ir.sharif.math.zahraSoukhtedel.util.Config;
import ir.sharif.math.zahraSoukhtedel.util.ImageUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MessageSendingController {
    private final ClientHandler clientHandler;
    private final MessageSendingValidator messageSendingValidator;
    static private final Logger logger = LogManager.getLogger(MessageSendingController.class);

    public MessageSendingController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        messageSendingValidator = new MessageSendingValidator();
    }

    private Chat createChat(User firstUser, User secondUser) throws DatabaseDisconnectException {
        for (Integer chatStateId : firstUser.getChatStates()) {
            ChatState chatState = Connector.getInstance().fetch(ChatState.class, chatStateId);
            Chat chat = Connector.getInstance().fetch(Chat.class, chatState.getChat());
            if (!chat.isGroup() && chat.getUsers().contains(firstUser.getId()) && chat.getUsers().contains(secondUser.getId()))
                return chat;
        }
        Chat chat = new Chat("", false);
        Connector.getInstance().save(chat);

        chat.addUser(firstUser.getId());
        Connector.getInstance().save(chat);

        chat.addUser(secondUser.getId());
        Connector.getInstance().save(chat);

        ChatState firstChatState = new ChatState(chat.getId());
        Connector.getInstance().save(firstChatState);
        firstUser.addChatState(firstChatState.getId());
        Connector.getInstance().save(firstUser);

        ChatState secondChatState = new ChatState(chat.getId());
        Connector.getInstance().save(secondChatState);
        secondUser.addChatState(secondChatState.getId());
        Connector.getInstance().save(secondUser);

        return chat;
    }

    public Response addMessage(String avatarString, String messageContent, Integer receiverId, LocalDateTime dateTime, boolean isTiming) {
        try {
            User writer = clientHandler.getUser();
            Integer imageID = null;
            if (avatarString != null) {
                ImageUtils imageUtils = new ImageUtils();
                try {
                    BufferedImage bufferedImage = imageUtils.toBufferedImage(avatarString);
                    ImageLoader imageLoader = new ImageLoader();
                    imageID = imageLoader.saveIntoDB(bufferedImage);
                } catch (IOException e) {
                    logger.warn("can't convert byte array to buffered image");
                    e.printStackTrace();
                }
            }
            if (receiverId != null) {
                User receiver = Connector.getInstance().fetch(User.class, receiverId);
                String error = messageSendingValidator.SendMessageError(writer, receiver);
                if (error.equals("")) {
                    Chat chat = createChat(writer, receiver);
                    //#########################################
                    Message message;
                    if(!isTiming) {
                        message = new Message(messageContent, writer.getId(), null, imageID, false);
                    }else{
                        message = new Message(messageContent, writer.getId(), null, imageID, false, dateTime, isTiming);
                    }
                    //##########################################
                    Connector.getInstance().save(message);

                    chat.addMessage(message.getId());
                    Connector.getInstance().save(chat);

                    return new BackResponse();
                } else //error occurred
                    return new NewMessageResponse(error, null, null, null);
            } else {
                //#########################################
                Message message;
                if(!isTiming) {
                    message = new Message(messageContent, writer.getId(), null, imageID, false);
                }else{
                    message = new Message(messageContent, writer.getId(), null, imageID, false, dateTime, isTiming);
                }
                //##########################################
                Connector.getInstance().save(message);
                return new NewMessageResponse("", message.getId(), getGroups(), getChats());
            }
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("server").getProperty("databaseDisconnectError"));
        }
    }

    private String chatName(User user, Chat chat) throws DatabaseDisconnectException {
        if (chat.isGroup() || chat.getUsers().size() == 1)
            return chat.getChatName();
        else if (chat.getUsers().get(0).equals(user.getId()))
            return Connector.getInstance().fetch(User.class, chat.getUsers().get(1)).getUsername();
        else
            return Connector.getInstance().fetch(User.class, chat.getUsers().get(0)).getUsername();
    }

    private List<ViewChat> getChats() throws DatabaseDisconnectException{
        User user = clientHandler.getUser();
        //adding chats
        List<ViewChat> chats = new ArrayList<>();
        MessagingValidator messagingValidator = new MessagingValidator();
        List<ChatState> validatedChatStates = messagingValidator.getValidatedChatStates(user);
        for (ChatState chatState : validatedChatStates) {
            Chat chat = Connector.getInstance().fetch(Chat.class, chatState.getChat());
            chats.add(new ViewChat(chat.getId(), chatName(user, chat), 0));
        }
        return chats;
    }

    private List<ViewGroup> getGroups() throws DatabaseDisconnectException{
        User user = clientHandler.getUser();
        //adding groups
        List<ViewGroup> groups = new ArrayList<>();
        for (Integer groupId : user.getGroups()) {
            Group group = Connector.getInstance().fetch(Group.class, groupId);
            groups.add(new ViewGroup(group.getGroupName(), groupId));
        }
        return groups;
    }

    public Response saveTweet(Integer tweetID) {
        try {
            Tweet tweet = Connector.getInstance().fetch(Tweet.class, tweetID);
            User user = clientHandler.getUser();

            Message message = new Message(tweet.getContent(), user.getId(), tweetID, tweet.getImage(), true);
            Connector.getInstance().save(message);

            Chat savedMessages = getSavedMessages(user);
            savedMessages.addMessage(message.getId());
            Connector.getInstance().save(savedMessages);

            logger.info(String.format("user %s saved tweet %s", user.getUsername(), tweet.getId()));
            return new TweetActionResponse(Config.getConfig("tweets").getProperty("successfulSave"), false);
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("server").getProperty("databaseDisconnectError"));
        }
    }

    private Chat getSavedMessages(User user) throws DatabaseDisconnectException {
        for (Integer chatStateId : user.getChatStates()) {
            ChatState chatState = Connector.getInstance().fetch(ChatState.class, chatStateId);
            Chat chat = Connector.getInstance().fetch(Chat.class, chatState.getChat());
            if(!chat.isGroup() && chat.getUsers().size() == 1)
                return chat;
        }
        return null;
    }

    public Response forwardTweet(Integer tweetID) {
        try {
            Tweet tweet = Connector.getInstance().fetch(Tweet.class, tweetID);
            User user = clientHandler.getUser();
            Message message = new Message(tweet.getContent(), user.getId(), tweetID, tweet.getImage(), true);
            Connector.getInstance().save(message);

            return new NewMessageResponse("", message.getId(), getGroups(), getChats());
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("server").getProperty("databaseDisconnectError"));
        }
    }

    public Response forwardMessage(Integer messageID) {
        try {
            User user = clientHandler.getUser();
            Message message = Connector.getInstance().fetch(Message.class, messageID);

            Integer mainMedia = message.getMainMedia();
            if (mainMedia == null)
                mainMedia = message.getId();

            Message newMessage = new Message(message.getContent(), user.getId(), mainMedia,
                    message.getImage(), message.isForwardedTweet());
            Connector.getInstance().save(newMessage);
            return new NewMessageResponse("", newMessage.getId(), getGroups(), getChats());
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("server").getProperty("databaseDisconnectError"));
        }
    }
}
