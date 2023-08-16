package ir.sharif.math.zahraSoukhtedel.controller.settingsPage;

import ir.sharif.math.zahraSoukhtedel.controller.ClientHandler;
import ir.sharif.math.zahraSoukhtedel.exceptions.DatabaseDisconnectException;
import ir.sharif.math.zahraSoukhtedel.models.Chat;
import ir.sharif.math.zahraSoukhtedel.models.ChatState;
import ir.sharif.math.zahraSoukhtedel.models.Group;
import ir.sharif.math.zahraSoukhtedel.models.media.Message;
import ir.sharif.math.zahraSoukhtedel.models.media.Tweet;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.database.Connector;
import ir.sharif.math.zahraSoukhtedel.models.User;
import ir.sharif.math.zahraSoukhtedel.response.LogoutResponse;
import ir.sharif.math.zahraSoukhtedel.response.ShowErrorResponse;
import ir.sharif.math.zahraSoukhtedel.util.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SettingsController {
    static private final Logger logger = LogManager.getLogger(SettingsController.class);

    private final ClientHandler clientHandler;

    public SettingsController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public Response logout(boolean terminate) {
        try {
            User user = clientHandler.getUser();
            if (user == null)
                return new LogoutResponse(terminate);
            logger.info(String.format("user %s logged out from the app.", user.getUsername()));
            user.setLastSeen(LocalDateTime.now());
            Connector.getInstance().save(user);
            clientHandler.setUser(null);
            return new LogoutResponse(terminate);
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("server").getProperty("databaseDisconnectError"));
        }
    }

    public Response deleteAccount() {
        try {
            User user = clientHandler.getUser();
            clientHandler.setUser(null);
            deleteGroups(user.getId());
            deleteChatStates(user.getId());
            deleteTweets(user.getId());
            deleteMessages(user.getId());
            deleteChats(user.getId());
            deleteUser(user.getId());
            logger.info(String.format("user %s deleted his/her account.", user.getUsername()));
            return new LogoutResponse(false);
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("server").getProperty("databaseDisconnectError"));
        }
    }

    private void deleteGroups(Integer userID) throws DatabaseDisconnectException {
        User user = Connector.getInstance().fetch(User.class, userID);
        for (Integer groupId : user.getGroups()) {
            Group group = Connector.getInstance().fetch(Group.class, groupId);
            Connector.getInstance().delete(group);
        }
    }

    private void deleteChatStates(Integer userID) throws DatabaseDisconnectException {
        User user = Connector.getInstance().fetch(User.class, userID);
        for (Integer chatStateId : user.getChatStates()) {
            ChatState chatState = Connector.getInstance().fetch(ChatState.class, chatStateId);
            Connector.getInstance().delete(chatState);
        }
    }

    private void deleteTweets(Integer userID) throws DatabaseDisconnectException {
        List<Tweet> toBeDeletedTweets = new ArrayList<>();
        List<Tweet> allTweets = Connector.getInstance().fetchAll(Tweet.class);
        for (Tweet tweet : allTweets)
            if (isDependentTweet(tweet, userID))
                toBeDeletedTweets.add(tweet);
        for (Tweet tweet : toBeDeletedTweets)
            Connector.getInstance().delete(tweet);
    }

    private void deleteMessages(Integer userID) throws DatabaseDisconnectException {
        List<Message> allMessages = Connector.getInstance().fetchAll(Message.class);
        for (Message message : allMessages)
            if (message.getWriter().equals(userID))
                Connector.getInstance().delete(message);
    }

    private void deleteChats(Integer userID) throws DatabaseDisconnectException {
        List<Chat> allChats = Connector.getInstance().fetchAll(Chat.class);
        for (Chat currentChat : allChats)
            if (!currentChat.isGroup() && currentChat.getUsers().contains(userID))
                Connector.getInstance().delete(currentChat);
            else {
                List<Integer> toBeRemovedMessages = new ArrayList<>();
                for (Integer messageID : currentChat.getMessages())
                    if (Connector.getInstance().fetch(Message.class, messageID) == null)
                        toBeRemovedMessages.add(messageID);
                for (Integer messageID : toBeRemovedMessages)
                    currentChat.removeMessage(messageID);
                if (currentChat.getUsers().contains(userID))
                    currentChat.removeUser(userID);
                Connector.getInstance().save(currentChat);
            }
    }

    public void deleteUser(Integer userID) throws DatabaseDisconnectException {
        List<User> allUsers = Connector.getInstance().fetchAll(User.class);
        for (User currentUser : allUsers) {
            if (currentUser.getId().equals(userID))
                continue;
            removeUserDetails(currentUser, userID);
        }
        User user = Connector.getInstance().fetch(User.class, userID);
        Connector.getInstance().delete(user);
    }

    private void removeUserDetails(User user, Integer toBeDeletedUser) throws DatabaseDisconnectException {
        //followings
        if (user.getFollowings().contains(toBeDeletedUser)) {
            user.removeFromFollowings(toBeDeletedUser);
            Connector.getInstance().save(user);
        }
        //followers
        if (user.getFollowers().contains(toBeDeletedUser)) {
            user.removeFromFollowers(toBeDeletedUser);
            Connector.getInstance().save(user);
        }
        //blocklist
        if (user.getBlockList().contains(toBeDeletedUser)) {
            user.removeFromBlocklist(toBeDeletedUser);
            Connector.getInstance().save(user);
        }
        //tweets
        List<Integer> toBeDeletedTweets = new ArrayList<>();
        for (Integer tweetID : user.getTweets())
            if (Connector.getInstance().fetch(Tweet.class, tweetID) == null)
                toBeDeletedTweets.add(tweetID);
        for (Integer tweetID : toBeDeletedTweets)
            user.removeFromTweets(tweetID);
        Connector.getInstance().save(user);
        toBeDeletedTweets.clear();
        //request notifications
        List<String> toBeDeletedNotifications = new ArrayList<>();
        for (String requestNotification : user.getRequestNotifications()) {
            String acceptedName = String.format("user %s accepted your follow request!",
                    Connector.getInstance().fetch(User.class, toBeDeletedUser).getUsername());
            String rejectedName = String.format("user %s rejected your follow request!",
                    Connector.getInstance().fetch(User.class, toBeDeletedUser).getUsername());
            if (requestNotification.equals(acceptedName) || requestNotification.equals(rejectedName))
                toBeDeletedNotifications.add(requestNotification);
        }
        for (String requestNotification : toBeDeletedNotifications)
            user.removeFromRequestNotifications(requestNotification);
        toBeDeletedNotifications.clear();
        Connector.getInstance().save(user);
        //liked tweets
        for (Integer tweetID : user.getLikedTweets())
            if (Connector.getInstance().fetch(Tweet.class, tweetID) == null)
                toBeDeletedTweets.add(tweetID);
        for (Integer tweetID : toBeDeletedTweets)
            user.removeFromLikedTweets(tweetID);
        Connector.getInstance().save(user);
        toBeDeletedTweets.clear();
        //requests
        if (user.getRequests().contains(toBeDeletedUser))
            user.removeFromRequests(toBeDeletedUser);
        Connector.getInstance().save(user);
        //notifications
        for (String notification : user.getNotifications()) {
            String followName = String.format("user %s followed you!",
                    Connector.getInstance().fetch(User.class, toBeDeletedUser).getUsername());
            String unfollowName = String.format("user %s unfollowed you!",
                    Connector.getInstance().fetch(User.class, toBeDeletedUser).getUsername());
            if (notification.equals(followName) || notification.equals(unfollowName))
                toBeDeletedNotifications.add(notification);
        }
        for (String notification : toBeDeletedNotifications)
            user.removeFromNotifications(notification);
        Connector.getInstance().save(user);
        toBeDeletedNotifications.clear();
        //groups
        List<Group> modifiedGroups = new ArrayList<>();
        for (Integer groupId : user.getGroups()) {
            Group group = Connector.getInstance().fetch(Group.class, groupId);
            if (group.getUsers().contains(toBeDeletedUser))
                modifiedGroups.add(group);
        }
        for (Group group : modifiedGroups) {
            group.removeUser(toBeDeletedUser);
            Connector.getInstance().save(group);
            user.removeGroup(group.getId());
            user.addGroup(group.getId());
            Connector.getInstance().save(user);
        }
        //chats
        List<ChatState> chatStatesToBeRemoved = new ArrayList<>();
        for (Integer chatStateId : user.getChatStates()) {
            ChatState chatState = Connector.getInstance().fetch(ChatState.class, chatStateId);
            if (chatState == null || Connector.getInstance().fetch(Chat.class, chatState.getChat()) == null)
                chatStatesToBeRemoved.add(chatState);
        }
        for (ChatState chatState : chatStatesToBeRemoved)
            if (chatState != null) {
                user.removeFromChatStates(chatState.getId());
                Connector.getInstance().delete(chatState);
            }
        Connector.getInstance().save(user);
    }

    private boolean isDependentTweet(Tweet tweet, Integer userID) throws DatabaseDisconnectException {
        if (tweet.getWriter().equals(userID))
            return true;
        if (tweet.getUpPost() == null)
            return false;
        return isDependentTweet(Connector.getInstance().fetch(Tweet.class, tweet.getUpPost()), userID);
    }


}
