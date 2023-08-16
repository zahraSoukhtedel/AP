package ir.sharif.math.zahraSoukhtedel.response;

import ir.sharif.math.zahraSoukhtedel.models.events.SwitchToProfileType;
import ir.sharif.math.zahraSoukhtedel.models.viewModels.*;
import ir.sharif.math.zahraSoukhtedel.models.viewModels.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ResponseVisitor {
    void goTo(String pageName, String message);
    void showError(String message);
    void updatePersonalPage(String bytes, List<ViewTweet> viewTweetList);
    void enter(boolean success, String message);
    void logout(boolean terminate);
    void switchToEditPage(String username, String firstname, String lastname, String bio, LocalDate birthdate,
                          String email, String phoneNumber, String lastSeenType, boolean accountPrivacy, boolean dataPrivacy);
    void applyEditResponse(String error);
    void updateNotificationsPage(List<String> requestMessages, List<String> systemMessages, List<ViewUser> requests);
    void updateListsPage(List<ViewUser> followers, List<ViewUser> followings, List<ViewUser> blocklist, List<ViewGroup> groups);
    void updateGroupPage(List<ViewUser> members);
    void applyCreateGroupResponse(String error);
    void applyEditGroupResponse(String error);
    void switchToProfilePage(SwitchToProfileType switchToProfileType, boolean exists, boolean mine, String error,
                             Integer userToBeVisited);
    void updateProfilePage(String username, String avatarString, String firstname, String lastname, String lastSeen,
                           String bio, String birthdate, String email, String phoneNumber, String blockString,
                           String muteString, String followString);
    void back();
    void updateTweetPage(String tweetContent, String tweetDate, String retweetString, String tweetImage, int likeNumbers, List<ViewTweet> viewTweetList, String likeButtonText);
    void applyTweetActionResponse(String verdict, boolean isError);
    void updateTimelinePage(List<ViewTweet> viewTweetList);
    void updateExplorerPage(List<ViewTweet> viewTweetList);
    void switchToSettingsPage(boolean isPrivate, String lastSeenType, String password);
    void updateMessagingPage(boolean isChanged, Integer chatId, String chatName, List<ViewChat> chats, List<ViewMessage> messages);
    void applyEditChatGroupResponse(String error);
    void applyNewMessageResponse(String error, Integer messageId, List<ViewGroup> viewGroups, List<ViewChat> chats);
    void updateMessageViewerPage(Integer messageId, boolean deactivated, String messageImage, String messageContent, LocalDateTime messageDateTime, String messageSender);
    void applyEditMessageResponse(String error);}
