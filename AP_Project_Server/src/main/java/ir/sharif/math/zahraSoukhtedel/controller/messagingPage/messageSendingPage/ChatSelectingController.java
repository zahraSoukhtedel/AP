package ir.sharif.math.zahraSoukhtedel.controller.messagingPage.messageSendingPage;

import ir.sharif.math.zahraSoukhtedel.controller.ClientHandler;
import ir.sharif.math.zahraSoukhtedel.database.Connector;
import ir.sharif.math.zahraSoukhtedel.exceptions.DatabaseDisconnectException;
import ir.sharif.math.zahraSoukhtedel.models.Chat;
import ir.sharif.math.zahraSoukhtedel.models.ChatState;
import ir.sharif.math.zahraSoukhtedel.models.media.Message;
import ir.sharif.math.zahraSoukhtedel.response.BackResponse;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.response.ShowErrorResponse;
import ir.sharif.math.zahraSoukhtedel.models.Group;
import ir.sharif.math.zahraSoukhtedel.models.User;
import ir.sharif.math.zahraSoukhtedel.util.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.List;

public class ChatSelectingController {
    private final ClientHandler clientHandler;
    static private final Logger logger = LogManager.getLogger(ChatSelectingController.class);

    public ChatSelectingController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public Response sendMessage(Integer messageId, List<Integer> chats, List<Integer> groups) {
        try {
            User writer = clientHandler.getUser();
            //first create chats.
            for (Integer groupId : groups) {
                Group group = Connector.getInstance().fetch(Group.class, groupId);
                for (Integer userID : group.getUsers()) {
                    User receiver = Connector.getInstance().fetch(User.class, userID);
                    MessageSendingValidator messageSendingValidator = new MessageSendingValidator();
                    if (!messageSendingValidator.SendMessageError(writer, receiver).equals(""))
                        continue;
                    Chat chat = createChat(writer, receiver);
                    if (!chats.contains(chat.getId()))
                        chats.add(chat.getId());
                }
            }
            //sending message to different chats.
            Message message = Connector.getInstance().fetch(Message.class, messageId);
            //################################################
            if (!message.isTiming()){
                message.setDateTime(LocalDateTime.now());
            }
            //#################################################
            Connector.getInstance().save(message);

            for (Integer chatID : chats) {
                Chat chat = Connector.getInstance().fetch(Chat.class, chatID);
                chat.addMessage(message.getId());
                Connector.getInstance().save(chat);
            }
            logger.info(String.format("user %s sent message %s to some chats.", writer.getUsername(), message.getId()));
            return new BackResponse();
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("serverConfig").getProperty(Config.class,"server").getProperty("databaseDisconnectError"));
        }
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
}
