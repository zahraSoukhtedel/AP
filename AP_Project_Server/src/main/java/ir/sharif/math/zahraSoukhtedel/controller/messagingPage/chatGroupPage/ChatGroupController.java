package ir.sharif.math.zahraSoukhtedel.controller.messagingPage.chatGroupPage;

import ir.sharif.math.zahraSoukhtedel.controller.ClientHandler;
import ir.sharif.math.zahraSoukhtedel.database.Connector;
import ir.sharif.math.zahraSoukhtedel.exceptions.DatabaseDisconnectException;
import ir.sharif.math.zahraSoukhtedel.models.Chat;
import ir.sharif.math.zahraSoukhtedel.models.ChatState;
import ir.sharif.math.zahraSoukhtedel.response.BackResponse;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.response.ShowErrorResponse;
import ir.sharif.math.zahraSoukhtedel.response.messagingPage.chatGroupPage.ChatGroupEditResponse;
import ir.sharif.math.zahraSoukhtedel.models.User;
import ir.sharif.math.zahraSoukhtedel.models.events.ChatGroupEventType;
import ir.sharif.math.zahraSoukhtedel.util.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChatGroupController {
    private final ClientHandler clientHandler;
    static private final Logger logger = LogManager.getLogger(ChatGroupController.class);

    public ChatGroupController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }


    public Response handle(ChatGroupEventType chatGroupEventType, String groupName, String username) {
        try {
            switch (chatGroupEventType) {
                case ADD:
                    return addUser(groupName, username);
                case CREATE:
                    return newChatGroup(groupName);
                case REMOVE:
                    return removeGroup(groupName);
            }
            return null;
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("server").getProperty("databaseDisconnectError"));
        }
    }

    Config errorsConfig = Config.getConfig("chatGroupPage");

    private Response newChatGroup(String groupName) throws DatabaseDisconnectException {
        User user = clientHandler.getUser();
        for (Integer chatStateId : user.getChatStates()) {
            ChatState chatState = Connector.getInstance().fetch(ChatState.class, chatStateId);
            Chat chat = Connector.getInstance().fetch(Chat.class, chatState.getChat());
            if (chat.isGroup() && chat.getChatName().equals(groupName)) {
                logger.info(errorsConfig.getProperty("groupWithSameNameError"));
                return new ChatGroupEditResponse(errorsConfig.getProperty("groupWithSameNameError"));
            }
        }
        Chat chat = new Chat(groupName, true);
        Connector.getInstance().save(chat);

        chat.addUser(user.getId());
        Connector.getInstance().save(chat);

        ChatState currentChatState = new ChatState(chat.getId());
        Connector.getInstance().save(currentChatState);

        user.addChatState(currentChatState.getId());
        Connector.getInstance().save(user);

        logger.info(String.format("user %s made group %s", user.getUsername(), chat.getChatName()));
        return new BackResponse();
    }

    public Response addUser(String groupName, String username) throws DatabaseDisconnectException {
        User user = clientHandler.getUser();
        //Chat chat = findGroup(user, groupName);
        Chat chat = Connector.getInstance().getChatByName(groupName);
        if (chat == null) {
            logger.info(errorsConfig.getProperty("groupNotExistError"));
            return new ChatGroupEditResponse(errorsConfig.getProperty("groupNotExistError"));
        }

        User userToBeAdded = Connector.getInstance().getUserByUsername(username);
        if (userToBeAdded == null || !userToBeAdded.isActive()) {
            logger.info(errorsConfig.getProperty("userNotExistError"));
            return new ChatGroupEditResponse(errorsConfig.getProperty("userNotExistError"));
        }

        if (chat.getUsers().contains(userToBeAdded.getId())) {
            logger.info(errorsConfig.getProperty("userAlreadyInGroupError"));
            return new ChatGroupEditResponse(errorsConfig.getProperty("userAlreadyInGroupError"));
        }
        if(!user.getId().equals(userToBeAdded.getId())) {
            if (!user.getFollowings().contains(userToBeAdded.getId())) {
                logger.info(errorsConfig.getProperty("userNotInFollowingsError"));
                return new ChatGroupEditResponse(errorsConfig.getProperty("userNotInFollowingsError"));
            }
        }

        chat.addUser(userToBeAdded.getId());
        Connector.getInstance().save(chat);

        ChatState currentChatState = new ChatState(chat.getId());
        Connector.getInstance().save(currentChatState);

        userToBeAdded.addChatState(currentChatState.getId());
        Connector.getInstance().save(userToBeAdded);

        logger.info(String.format("user %s added user %s to group %s", user.getUsername(),
                userToBeAdded.getUsername(), chat.getChatName()));
        return new BackResponse();
    }

    public Response removeGroup(String groupName) throws DatabaseDisconnectException{
        User user = clientHandler.getUser();
        Chat chat = null;
        ChatState chatState = null;

        for (Integer chatStateId : user.getChatStates()) {
            ChatState currentChatState = Connector.getInstance().fetch(ChatState.class, chatStateId);
            Chat currentChat = Connector.getInstance().fetch(Chat.class, currentChatState.getChat());
            if (currentChat.getChatName().equals(groupName)) {
                chat = currentChat;
                chatState = currentChatState;
            }
        }

        if (chat == null) {
            logger.info(errorsConfig.getProperty("groupNotExistError"));
            return new ChatGroupEditResponse(errorsConfig.getProperty("groupNotExistError"));
        }

        chat.removeUser(user.getId());
        Connector.getInstance().save(chat);

        user.removeFromChatStates(chatState.getId());
        Connector.getInstance().save(user);

        Connector.getInstance().delete(chatState);
        return new BackResponse();
    }

    private Chat findGroup(User user, String groupName) throws DatabaseDisconnectException {
        for (Integer chatStateId : user.getChatStates()) {
            ChatState chatState = Connector.getInstance().fetch(ChatState.class, chatStateId);
            Chat chat = Connector.getInstance().fetch(Chat.class, chatState.getChat());
            if (chat.getChatName().equals(groupName))
                return chat;
        }
        return null;
    }
}
