package ir.sharif.math.zahraSoukhtedel.controller;

import ir.sharif.math.zahraSoukhtedel.controller.enterPage.EnterController;
import ir.sharif.math.zahraSoukhtedel.controller.explorerPage.ExplorerController;
import ir.sharif.math.zahraSoukhtedel.controller.messagingPage.EditMessageController;
import ir.sharif.math.zahraSoukhtedel.controller.messagingPage.MessageViewerController;
import ir.sharif.math.zahraSoukhtedel.controller.messagingPage.MessagingController;
import ir.sharif.math.zahraSoukhtedel.controller.messagingPage.chatGroupPage.ChatGroupController;
import ir.sharif.math.zahraSoukhtedel.controller.messagingPage.messageSendingPage.MessageSendingController;
import ir.sharif.math.zahraSoukhtedel.controller.network.RequestSender;
import ir.sharif.math.zahraSoukhtedel.controller.network.SocketRequestSender;
import ir.sharif.math.zahraSoukhtedel.controller.personalPage.MyPageController;
import ir.sharif.math.zahraSoukhtedel.controller.personalPage.editPage.EditPageController;
import ir.sharif.math.zahraSoukhtedel.controller.personalPage.listsPage.GroupPageController;
import ir.sharif.math.zahraSoukhtedel.controller.personalPage.listsPage.ListsPageController;
import ir.sharif.math.zahraSoukhtedel.controller.personalPage.listsPage.NewGroupController;
import ir.sharif.math.zahraSoukhtedel.controller.personalPage.notificationsPage.NotificationsPageController;
import ir.sharif.math.zahraSoukhtedel.controller.profileView.ProfileViewController;
import ir.sharif.math.zahraSoukhtedel.controller.settingsPage.PrivacySettingsController;
import ir.sharif.math.zahraSoukhtedel.controller.timelinePage.TimelineController;
import ir.sharif.math.zahraSoukhtedel.controller.tweets.TweetManager;
import ir.sharif.math.zahraSoukhtedel.exceptions.ClientDisconnectException;
import ir.sharif.math.zahraSoukhtedel.model.ChatOffline;
import ir.sharif.math.zahraSoukhtedel.model.GroupOffline;
import ir.sharif.math.zahraSoukhtedel.model.MessageOffline;
import ir.sharif.math.zahraSoukhtedel.model.UserOffline;
import ir.sharif.math.zahraSoukhtedel.models.events.SwitchToProfileType;
import ir.sharif.math.zahraSoukhtedel.models.viewModels.*;
import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.request.UpdatePageRequest;
import ir.sharif.math.zahraSoukhtedel.request.enterPage.LoginRequest;
import ir.sharif.math.zahraSoukhtedel.request.messagingPage.UpdateMessageViewerPageRequest;
import ir.sharif.math.zahraSoukhtedel.request.messagingPage.UpdateMessagingPageRequest;
import ir.sharif.math.zahraSoukhtedel.request.messagingPage.messageSendingPage.SendMessageRequest;
import ir.sharif.math.zahraSoukhtedel.request.personalPage.editPage.UpdateProfilePageRequest;
import ir.sharif.math.zahraSoukhtedel.request.personalPage.listsPage.UpdateGroupPageRequest;
import ir.sharif.math.zahraSoukhtedel.request.tweets.UpdateTweetPageRequest;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.response.ResponseVisitor;
import ir.sharif.math.zahraSoukhtedel.response.messagingPage.messageSendingPage.NewMessageResponse;
import ir.sharif.math.zahraSoukhtedel.util.Config;
import ir.sharif.math.zahraSoukhtedel.util.Loop;
import ir.sharif.math.zahraSoukhtedel.view.FXController;
import ir.sharif.math.zahraSoukhtedel.view.Page;
import ir.sharif.math.zahraSoukhtedel.view.ViewManager;
import ir.sharif.math.zahraSoukhtedel.view.explorerPage.ExplorerFXController;
import ir.sharif.math.zahraSoukhtedel.view.messagingPage.MessageViewerFXController;
import ir.sharif.math.zahraSoukhtedel.view.messagingPage.MessagingFXController;
import ir.sharif.math.zahraSoukhtedel.view.messagingPage.messageSendingPage.ChatSelectingFXController;
import ir.sharif.math.zahraSoukhtedel.view.personalPage.MyFXController;
import ir.sharif.math.zahraSoukhtedel.view.personalPage.listsPage.GroupFXController;
import ir.sharif.math.zahraSoukhtedel.view.personalPage.listsPage.ListsFXController;
import ir.sharif.math.zahraSoukhtedel.view.personalPage.notificationsPage.NotificationsFXController;
import ir.sharif.math.zahraSoukhtedel.view.profileView.ProfileFXController;
import ir.sharif.math.zahraSoukhtedel.view.timelinePage.TimelineFXController;
import ir.sharif.math.zahraSoukhtedel.view.tweets.TweetFXController;
import javafx.application.Platform;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Client implements ResponseVisitor {
    @Getter @Setter
    static private HashMap<Integer,List<Request>> messageRequests = new HashMap<>();
    @Getter @Setter
    static private List<Request> offlineRequests;
    @Getter @Setter
    static private UserOffline userOffline;
    @Getter @Setter
    static private boolean isLoadedSettingPage = false;
    @Getter @Setter
    static private boolean isOnline = true;

    @Getter @Setter
    private static String userName;

    static private final Logger logger = LogManager.getLogger(ResponseVisitor.class);

    @Getter
    private static Client client;
    private RequestSender requestSender;
    private final LinkedList<Request> requestsList;
    @Getter
    private final Loop loop, updater;

    private final EnterController enterController;
    private final MyPageController myPageController;
    private final EditPageController editPageController;
    private final NotificationsPageController notificationsPageController;
    private final ListsPageController listsPageController;
    private final GroupPageController groupPageController;
    private final NewGroupController newGroupController;
    private final ProfileViewController profileViewController;
    private final TimelineController timelineController;
    private final ExplorerController explorerController;
    private final PrivacySettingsController privacySettingsController;
    private final MessagingController messagingController;
    private final ChatGroupController chatGroupController;
    private final MessageSendingController messageSendingController;
    private final MessageViewerController messageViewerController;
    private final EditMessageController editMessageController;

    public Client(RequestSender requestSender) {
        this.requestSender = requestSender;
        this.requestsList = new LinkedList<>();
        this.loop = new Loop(Config.getConfig("clientConfig").getProperty(Config.class, "client").getProperty(Double.class, "loopFps"),
                this::sendRequests);
        this.updater = new Loop(Config.getConfig("clientConfig").getProperty(Config.class, "client").getProperty(Double.class, "updaterFps"),
                this::updatePage);
        client = this;

        enterController = new EnterController();
        myPageController = new MyPageController();
        editPageController = new EditPageController();
        notificationsPageController = new NotificationsPageController();
        listsPageController = new ListsPageController();
        groupPageController = new GroupPageController();
        newGroupController = new NewGroupController();
        profileViewController = new ProfileViewController();
        timelineController = new TimelineController();
        explorerController = new ExplorerController();
        privacySettingsController = new PrivacySettingsController();
        messagingController = new MessagingController();
        chatGroupController = new ChatGroupController();
        messageSendingController = new MessageSendingController();
        messageViewerController = new MessageViewerController();
        editMessageController = new EditMessageController();

        userOffline = new UserOffline(this);
    }

    public void start(Stage stage) {
        loop.start();
        updater.start();
        ViewManager.getInstance().start(stage);
    }

    public void reStart(Socket socket){
        try {
            this.requestSender = new SocketRequestSender(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setOnline(true);
        userOffline.setNewMessages(new ArrayList<>());
        userOffline.setLoadedChats(new ArrayList<>());
        userOffline.setLoadedGroups(new ArrayList<>());

        try {
            Response response = requestSender.sendRequest(new LoginRequest(userOffline.getUsername(), userOffline.getPassword()));
            if (response != null)
                response.visit(this);
        } catch (ClientDisconnectException e) {
            e.printStackTrace();
        }

        for (Integer i : Client.getMessageRequests().keySet()){
            List<Request> l = Client.getMessageRequests().get(i);
            try {
                NewMessageResponse response = (NewMessageResponse)requestSender.sendRequest(l.get(0));
                Integer id = response.getMessageId();
                Request re = new SendMessageRequest(id,((SendMessageRequest)l.get(1)).getChats() , ((SendMessageRequest)l.get(1)).getGroups());
                Response t = requestSender.sendRequest(re);
                //************888
            } catch (ClientDisconnectException e) {
                e.printStackTrace();
            }
        }
        messageRequests = new HashMap<>();
        loop.restart();
    }

    public void addRequest(Request request) {
        synchronized (requestsList) {
            requestsList.add(request);
        }
    }

    private void sendRequests() {
        List<Request> tmpRequestsList;
        synchronized (requestsList) {
            tmpRequestsList = new LinkedList<>(requestsList);
            requestsList.clear();
        }
        try {
            for (Request request : tmpRequestsList) {
                Response response;
                response = requestSender.sendRequest(request);
                if (response != null)
                    response.visit(this);
            }
        } catch (ClientDisconnectException e) {
            logger.info("client program terminated");
            loop.stop();
            isOnline = false;
        }
    }

    private void updatePage() {
        if (ViewManager.getInstance().getCurPage() == null)
            return;
        FXController fxController = ViewManager.getInstance().getCurPage().getFxController();
        if (fxController instanceof GroupFXController){
            addRequest(new UpdateGroupPageRequest(((GroupFXController) fxController).getGroup()));
        } else if (fxController instanceof ProfileFXController){
            addRequest(new UpdateProfilePageRequest(((ProfileFXController) fxController).getUserToVeVisited()));
        }
        else if(fxController instanceof TweetFXController){
            addRequest(new UpdateTweetPageRequest(((TweetFXController) fxController).getTweetID(),
                    ((TweetFXController) fxController).isMyTweets()));
        }
        else if(fxController instanceof MessagingFXController){
            if (Client.isOnline()){
                addRequest(new UpdateMessagingPageRequest(((MessagingFXController) fxController).getSelectedChatID(), false));
            }else {
                updateMessagingOffline(((MessagingFXController) fxController).getSelectedChatID(), false);
            }
        }
        else if(fxController instanceof MessageViewerFXController){
            if (Client.isOnline()){
                addRequest(new UpdateMessageViewerPageRequest(((MessageViewerFXController) fxController).getMessageID()));
            }else {
                updateMessageViewerPageOffline(((MessageViewerFXController) fxController).getMessageID());
            }
        }
        else if (fxController instanceof MyFXController || fxController instanceof ListsFXController ||
                fxController instanceof NotificationsFXController || fxController instanceof TimelineFXController ||
                fxController instanceof ExplorerFXController || fxController instanceof ChatSelectingFXController){
            addRequest(new UpdatePageRequest(fxController.getClass().getSimpleName()));
        }
    }

    public void updateMessagingOffline(Integer chatId, boolean isChanged){
        String chatName = null;
        List<ViewMessage> viewMessages = new ArrayList<>();
        List<ViewChat> viewChats = new ArrayList<>();
        for (ChatOffline chatOffline : userOffline.getLoadedChats()){
            viewChats.add(chatOffline.getViewChat());
            if (chatOffline.getViewChat().getChatId().equals(chatId)){
                chatName = chatOffline.getViewChat().getChatName();
                for (MessageOffline m: chatOffline.getLoadedMessages()){
                    viewMessages.add(m.getViewMessage());
                }
                for (MessageOffline m : chatOffline.getUnSendMessages()){
                    viewMessages.add(m.getViewMessage());
                }
            }
        }
        messagingController.refresh(isChanged, chatId, chatName, viewChats, viewMessages);
    }

    @Override
    public void goTo(String pageName, String message) {
        Platform.runLater(() -> {
            ViewManager.getInstance().setPage(new Page(pageName));
            if (message.length() > 0)
                ViewManager.getInstance().showInformation(message);
        });
    }

    @Override
    public void showError(String message) {
        Platform.runLater(() -> ViewManager.getInstance().showError(message));
    }

    @Override
    public void updatePersonalPage(String bytes, List<ViewTweet> viewTweetList) {
        myPageController.refresh(bytes, viewTweetList);
    }

    @Override
    public void enter(boolean success, String message) {
        enterController.enter(success, message);
    }

    @Override
    public void logout(boolean terminate) {
        if(terminate)
            System.exit(0);
        else
            goTo("enterPage", "");
    }

    @Override
    public void switchToEditPage(String username, String firstname, String lastname, String bio,
                                 LocalDate birthdate, String email, String phoneNumber, String lastSeenType,
                                 boolean isPrivate, boolean isPublicData) {
        editPageController.switchToEditPage(username, firstname, lastname, bio, birthdate, email, phoneNumber, lastSeenType,
                isPrivate, isPublicData);
    }

    @Override
    public void applyEditResponse(String error) {
        editPageController.applyEditResponse(error);
    }

    @Override
    public void updateNotificationsPage(List<String> requestMessages, List<String> systemMessages, List<ViewUser> requests) {
        notificationsPageController.refresh(requestMessages, systemMessages, requests);
    }

    @Override
    public void updateListsPage(List<ViewUser> followers, List<ViewUser> followings, List<ViewUser> blocklist, List<ViewGroup> groups) {
        //************************************************************************
        List<GroupOffline> groupOfflines = new ArrayList<>();
        for (ViewGroup viewGroup : groups){
            groupOfflines.add(new GroupOffline(viewGroup));
        }
        userOffline.setLoadedGroups(groupOfflines);
        //*************************************************************************
        listsPageController.refresh(followers, followings, blocklist, groups);
    }

    @Override
    public void updateGroupPage(List<ViewUser> members) {
        groupPageController.refresh(members);
    }

    @Override
    public void applyCreateGroupResponse(String error) {
        newGroupController.applyCreateGroupResponse(error);
    }

    @Override
    public void applyEditGroupResponse(String error) {
        groupPageController.applyEditGroupResponse(error);
    }

    @Override
    public void switchToProfilePage(SwitchToProfileType switchToProfileType, boolean exists, boolean mine, String error,
                                    Integer userToBeVisited) {
        profileViewController.switchToProfilePage(switchToProfileType, exists, mine, error, userToBeVisited);
    }

    @Override
    public void updateProfilePage(String username, String avatarString, String firstname, String lastname, String lastSeen,
                                  String bio, String birthdate, String email, String phoneNumber, String blockString,
                                  String muteString, String followString) {
        profileViewController.refresh(username, avatarString, firstname, lastname, lastSeen, bio, birthdate, email,
                phoneNumber, blockString, muteString, followString);
    }

    @Override
    public void back() {
        Platform.runLater(() -> ViewManager.getInstance().back());
    }

    @Override
    public void updateTweetPage(String tweetContent, String tweetDate, String retweetString, String tweetImage,
                                int likeNumbers, List<ViewTweet> viewTweetList, String likeButtonText) {
        TweetManager.getInstance().refresh(tweetContent, tweetDate, retweetString, tweetImage, likeNumbers, viewTweetList,
                likeButtonText);
    }

    @Override
    public void applyTweetActionResponse(String verdict, boolean isError) {
        TweetManager.getInstance().applyTweetActionResponse(verdict, isError);
    }

    @Override
    public void updateTimelinePage(List<ViewTweet> viewTweetList) {
        timelineController.refresh(viewTweetList);
    }

    @Override
    public void updateExplorerPage(List<ViewTweet> viewTweetList) {
        explorerController.refresh(viewTweetList);
    }

    @Override
    public void switchToSettingsPage(boolean isPrivate, String lastSeenType, String password) {
        //*************************************************************************
        userOffline.setPublicData(!isPrivate);
        userOffline.setLastSeenType(lastSeenType);
        userOffline.setPassword(password);
        //**************************************************************************
        privacySettingsController.switchToPrivacySettingsPage(isPrivate, lastSeenType, password);
    }

    @Override
    public void updateMessagingPage(boolean isChanged, Integer chatId, String chatName, List<ViewChat> chats, List<ViewMessage> messages) {
        //**************************************************************************************
        List<ChatOffline> l = userOffline.getLoadedChats();
        for (ViewChat viewChat : chats){
            boolean f = false;
            for (ChatOffline c: l){
                if (c.getViewChat().getChatId().equals(viewChat.getChatId())){
                    f = true;
                    break;
                }
            }
            if (!f){
                ChatOffline chatOffline = new ChatOffline(viewChat);
                l.add(chatOffline);
            }
            //TODO check
            if (viewChat.getChatId().equals(chatId)){
                for (ChatOffline chatOffline : userOffline.getLoadedChats()){
                    if (chatOffline.getViewChat().getChatId().equals(chatId)){
                        for (ViewMessage m : messages){
                            boolean b = false;
                            for (MessageOffline m1 : chatOffline.getLoadedMessages()){
                                if (m.getMessageId().equals(m1.getViewMessage().getMessageId())){
                                    b = true;
                                    break;
                                }
                            }
                            if (!b){
                                chatOffline.getLoadedMessages().add(new MessageOffline(m));
                            }
                        }
                    }
                }
            }
        }
        //*************************************************************************************
        messagingController.refresh(isChanged, chatId, chatName, chats, messages);
    }

    @Override
    public void applyEditChatGroupResponse(String error) {
        chatGroupController.applyError(error);
    }

    @Override
    public void applyNewMessageResponse(String error, Integer messageId, List<ViewGroup> groups, List<ViewChat> chats) {
        //#################################################################
        for (ViewChat viewChat : chats){
            boolean flag = false;
            for (ChatOffline c : Client.getUserOffline().getLoadedChats()){
                if (viewChat.getChatId().equals(c.getViewChat().getChatId())){
                    flag = true;
                    break;
                }
            }
            if(!flag){
                Client.getUserOffline().getLoadedChats().add(new ChatOffline(viewChat));
            }
        }
        for (ViewGroup viewGroup : groups){
            boolean flag = false;
            for (GroupOffline g : Client.getUserOffline().getLoadedGroups()){
                if (viewGroup.getGroupId().equals(g.getViewGroup().getGroupId())){
                    flag = true;
                    break;
                }
            }
            if (!flag){
                Client.getUserOffline().getLoadedGroups().add(new GroupOffline(viewGroup));
            }
        }
        //##################################################################
        messageSendingController.applyNewMessageResponse(error, messageId, groups, chats);
    }

    @Override
    public void updateMessageViewerPage(Integer messageId, boolean deactivated, String messageImage, String messageContent,
                                        LocalDateTime messageDateTime, String messageSender) {
        //**********************************************************************************************
        for(ChatOffline chatOffline : userOffline.getLoadedChats()){
            for (MessageOffline messageOffline : chatOffline.getLoadedMessages()){
                if (messageOffline.getViewMessage().getMessageId().equals(messageId)){
                    messageOffline.setImageAddress(messageImage);
                }
            }
        }
        //*********************************************************************************************
        messageViewerController.refresh(deactivated, messageImage, messageContent, messageDateTime, messageSender);
    }

    public void updateMessageViewerPageOffline(Integer messageId){
        String messageImage = null;
        String messageContent = null;
        LocalDateTime messageDateTime = null;
        String messageSender = null;

        for (ChatOffline chatOffline : userOffline.getLoadedChats()){
            for (MessageOffline m : chatOffline.getLoadedMessages()){
                if (m.getViewMessage().getMessageId().equals(messageId)){
                    messageImage = m.getImageAddress();
                    messageContent = m.getViewMessage().getMessageContent();
                    messageDateTime = m.getViewMessage().getMessageDateTime();
                    messageSender = m.getViewMessage().getMessageSender();
                }
            }
            for (MessageOffline m : chatOffline.getUnSendMessages()){
                if (m.getViewMessage().getMessageId().equals(messageId)){
                    messageImage = m.getImageAddress();
                    messageContent = m.getViewMessage().getMessageContent();
                    messageDateTime = m.getViewMessage().getMessageDateTime();
                    messageSender = m.getViewMessage().getMessageSender();
                }
            }
        }
        messageViewerController.refresh(false, messageImage, messageContent, messageDateTime, messageSender);
    }

    @Override
    public void applyEditMessageResponse(String error) {
        editMessageController.applyEditMessageResponse(error);
    }

}