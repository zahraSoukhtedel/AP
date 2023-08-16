package ir.sharif.math.zahraSoukhtedel.controller;

import ir.sharif.math.zahraSoukhtedel.controller.explorerPage.ExplorerController;
import ir.sharif.math.zahraSoukhtedel.controller.messagingPage.MessageViewerController;
import ir.sharif.math.zahraSoukhtedel.controller.messagingPage.messageSendingPage.MessageSendingController;
import ir.sharif.math.zahraSoukhtedel.controller.network.ResponseSender;
import ir.sharif.math.zahraSoukhtedel.exceptions.DatabaseDisconnectException;
import ir.sharif.math.zahraSoukhtedel.models.events.*;
import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.controller.enterPage.SignInController;
import ir.sharif.math.zahraSoukhtedel.controller.enterPage.SignUpController;
import ir.sharif.math.zahraSoukhtedel.controller.messagingPage.MessagingController;
import ir.sharif.math.zahraSoukhtedel.controller.messagingPage.chatGroupPage.ChatGroupController;
import ir.sharif.math.zahraSoukhtedel.controller.messagingPage.messageSendingPage.ChatSelectingController;
import ir.sharif.math.zahraSoukhtedel.controller.personalPage.MyPageController;
import ir.sharif.math.zahraSoukhtedel.controller.personalPage.editPage.EditPageController;
import ir.sharif.math.zahraSoukhtedel.controller.personalPage.listsPage.GroupPageController;
import ir.sharif.math.zahraSoukhtedel.controller.personalPage.listsPage.ListsPageController;
import ir.sharif.math.zahraSoukhtedel.controller.personalPage.listsPage.NewGroupController;
import ir.sharif.math.zahraSoukhtedel.controller.personalPage.notificationsPage.NotificationsPageController;
import ir.sharif.math.zahraSoukhtedel.controller.personalPage.notificationsPage.RequestController;
import ir.sharif.math.zahraSoukhtedel.controller.profileView.ProfileViewController;
import ir.sharif.math.zahraSoukhtedel.controller.settingsPage.PrivacySettingsController;
import ir.sharif.math.zahraSoukhtedel.controller.settingsPage.SettingsController;
import ir.sharif.math.zahraSoukhtedel.controller.timelinePage.TimelineController;
import ir.sharif.math.zahraSoukhtedel.controller.tweets.NewTweetController;
import ir.sharif.math.zahraSoukhtedel.controller.tweets.TweetManager;
import ir.sharif.math.zahraSoukhtedel.database.Connector;
import ir.sharif.math.zahraSoukhtedel.exceptions.ClientDisconnectException;
import ir.sharif.math.zahraSoukhtedel.models.User;
import ir.sharif.math.zahraSoukhtedel.request.RequestVisitor;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.response.ShowErrorResponse;
import ir.sharif.math.zahraSoukhtedel.util.Config;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ClientHandler extends Thread implements RequestVisitor {

    @Getter
    private final ResponseSender responseSender;
    private volatile boolean running;
    @Getter
    @Setter
    private User user;

    private final SignUpController signUpController;
    private final SignInController signInController;
    private final MyPageController myPageController;
    private final EditPageController editPageController;
    private final NotificationsPageController notificationsPageController;
    private final RequestController requestController;
    private final ListsPageController listsPageController;
    private final GroupPageController groupPageController;
    private final NewGroupController newGroupController;
    private final ProfileViewController profileViewController;
    private final NewTweetController newTweetController;
    private final TimelineController timelineController;
    private final ExplorerController explorerController;
    private final SettingsController settingsController;
    private final PrivacySettingsController privacySettingsController;
    private final MessagingController messagingController;
    private final ChatGroupController chatGroupController;
    private final MessageSendingController messageSendingController;
    private final ChatSelectingController chatSelectingController;
    private final MessageViewerController messageViewerController;

    public ClientHandler(ResponseSender responseSender) throws IOException {
        this.responseSender = responseSender;

        signUpController = new SignUpController(this);
        signInController = new SignInController(this);
        myPageController = new MyPageController(this);
        editPageController = new EditPageController(this);
        notificationsPageController = new NotificationsPageController(this);
        requestController = new RequestController(this);
        listsPageController = new ListsPageController(this);
        groupPageController = new GroupPageController(this);
        newGroupController = new NewGroupController(this);
        profileViewController = new ProfileViewController(this);
        newTweetController = new NewTweetController(this);
        timelineController = new TimelineController(this);
        explorerController = new ExplorerController(this);
        settingsController = new SettingsController(this);
        privacySettingsController = new PrivacySettingsController(this);
        messagingController = new MessagingController(this);
        chatGroupController = new ChatGroupController(this);
        messageSendingController = new MessageSendingController(this);
        chatSelectingController = new ChatSelectingController(this);
        messageViewerController = new MessageViewerController(this);
    }

    @Override
    public synchronized void start() {
        running = true;
        super.start();
    }

    @Override
    public void run() {
        while (running) {
            try {
                try {
                    Request request = responseSender.getRequest();
                    //updating user
                    if(user != null)
                        user  = Connector.getInstance().fetch(User.class, user.getId());
                    Response response = request.visit(this);
                    responseSender.sendResponse(response);
                } catch (DatabaseDisconnectException e) {
                    responseSender.sendResponse(new ShowErrorResponse(Config.getConfig("server").
                            getProperty("databaseDisconnectError")));
                }
            } catch (ClientDisconnectException e) {
                running = false;
            }
        }
        responseSender.close();
    }

    @Override
    public Response updatePage(String pageName) {
        switch (pageName) {
            case "MyFXController":
                return myPageController.getUpdate();
            case "NotificationsFXController":
                return notificationsPageController.getUpdate();
            case "ListsFXController":
                return listsPageController.getUpdate();
            case "TimelineFXController":
                return timelineController.getUpdate();
            case "ExplorerFXController":
                return explorerController.getUpdate();
            default:
                return null;
        }
    }

    @Override
    public Response login(String username, String password) {
        return signInController.login(username, password);
    }

    @Override
    public Response register(String username, String firstname, String lastname, String bio, LocalDate birthDate,
                             String email, String phoneNumber, String password, boolean publicData, String lastSeenType) {
        return signUpController.register(username, firstname, lastname, bio, birthDate, email, phoneNumber, password, publicData,
                lastSeenType);
    }

    @Override
    public Response logout(boolean terminate) { return settingsController.logout(terminate);}

    @Override
    public Response edit(String firstname, String lastname, String bio, LocalDate birthdate, String email,
                         String phoneNumber, String avatarString) {
        return editPageController.edit(firstname, lastname, bio, birthdate, email, phoneNumber, avatarString);
    }

    @Override
    public Response switchToEditPage() {
        return editPageController.getInfoToSwitch();
    }

    @Override
    public Response followRequestHandle(RequestAnswerType requestAnswerType, Integer requesterID) {
        return requestController.handle(requestAnswerType, requesterID);
    }

    @Override
    public Response updateGroupPage(Integer groupId) {
        return groupPageController.getUpdate(groupId);
    }

    @Override
    public Response createGroup(String groupName) {
        return newGroupController.createGroup(groupName);
    }

    @Override
    public Response editGroup(GroupPageEventType groupPageEventType, Integer group, String username) {
        return groupPageController.getEditResponse(groupPageEventType, group, username);
    }

    @Override
    public Response profileHandle(ProfilePageEventType profilePageEventType, Integer userToBeVisited) {
        return profileViewController.profileHandle(profilePageEventType, userToBeVisited);
    }

    @Override
    public Response switchToProfilePage(SwitchToProfileType switchToProfileType, Integer Id, String username) {
        return profileViewController.getInfoToSwitch(switchToProfileType, Id, username);
    }

    @Override
    public Response updateProfilePage(Integer userToBeVisited) {
        return profileViewController.getUpdate(userToBeVisited);
    }

    @Override
    public Response newTweet(String content, String avatarString, Integer upPost) {
        return newTweetController.addTweet(content, avatarString, upPost);
    }

    @Override
    public Response updateTweetPage(Integer tweetId, boolean myTweets) {
        return TweetManager.getInstance().getUpdate(tweetId, user, myTweets);
    }

    @Override
    public Response applyTweetAction(TweetPageEventType tweetPageEventType, Integer tweetId) {
        return TweetManager.getInstance().applyTweetAction(tweetPageEventType, user, tweetId);
    }

    @Override
    public Response switchToPrivacySettingsPage() {
        return privacySettingsController.getInfoToSwitch();
    }

    @Override
    public Response deactivate() {
        return privacySettingsController.deactivate();
    }

    @Override
    public Response editPrivacySettings(boolean isPrivate, String lastSeenType, String password) {
        return privacySettingsController.editPrivacySettingsResponse(isPrivate, lastSeenType, password);
    }

    @Override
    public Response updateMessagingPage(Integer chatId, boolean isChanged) {
        return messagingController.getUpdate(chatId, isChanged);
    }

    @Override
    public Response chatGroupRequestHandle(ChatGroupEventType chatGroupEventType, String groupName, String username) {
        return chatGroupController.handle(chatGroupEventType, groupName, username);
    }

    @Override
    public Response newMessage(String avatarString, String messageContent, Integer receiverId, LocalDateTime dateTime, boolean isTiming) {
        return messageSendingController.addMessage(avatarString, messageContent, receiverId, dateTime, isTiming);
    }

    @Override
    public Response sendMessage(Integer messageId, List<Integer> chats, List<Integer> groups) {
        return chatSelectingController.sendMessage(messageId, chats, groups);
    }

    @Override
    public Response saveTweet(Integer tweetId) {
        return messageSendingController.saveTweet(tweetId);
    }

    @Override
    public Response forwardTweet(Integer tweetId) {
        return messageSendingController.forwardTweet(tweetId);
    }

    @Override
    public Response updateMessageViewerPage(Integer messageId) {
        return messageViewerController.getUpdate(messageId);
    }

    @Override
    public Response deleteMessage(Integer messageId) {
        return messageViewerController.deleteMessage(messageId);
    }

    @Override
    public Response forwardMessage(Integer messageId) {
        return messageSendingController.forwardMessage(messageId);
    }

    @Override
    public Response editMessage(Integer messageId, String messageContent) {
        return messageViewerController.editMessage(messageId, messageContent);
    }

    @Override
    public Response deleteAccount() {
        return settingsController.deleteAccount();
    }

}
