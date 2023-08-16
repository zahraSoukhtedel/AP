package ir.sharif.math.zahraSoukhtedel.controller.profileView;

import ir.sharif.math.zahraSoukhtedel.controller.ClientHandler;
import ir.sharif.math.zahraSoukhtedel.database.Connector;
import ir.sharif.math.zahraSoukhtedel.database.ImageLoader;
import ir.sharif.math.zahraSoukhtedel.exceptions.DatabaseDisconnectException;
import ir.sharif.math.zahraSoukhtedel.models.events.SwitchToProfileType;
import ir.sharif.math.zahraSoukhtedel.models.media.Message;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.response.ShowErrorResponse;
import ir.sharif.math.zahraSoukhtedel.response.profileView.SwitchToProfilePageResponse;
import ir.sharif.math.zahraSoukhtedel.response.profileView.UpdateProfilePageResponse;
import ir.sharif.math.zahraSoukhtedel.models.User;
import ir.sharif.math.zahraSoukhtedel.models.events.ProfilePageEventType;
import ir.sharif.math.zahraSoukhtedel.models.media.Tweet;
import ir.sharif.math.zahraSoukhtedel.util.Config;
import ir.sharif.math.zahraSoukhtedel.util.ImageUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class ProfileViewController {
    static private final Logger logger = LogManager.getLogger(ProfileViewController.class);

    private final ClientHandler clientHandler;

    public ProfileViewController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public Response getInfoToSwitch(SwitchToProfileType switchToProfileType, Integer Id, String username) {
        try {
            switch (switchToProfileType) {
                case USER:
                    return getInfoToSwitchByUserId(Id);
                case TWEET:
                    return getInfoToSwitchByTweetId(Id);
                case MESSAGE:
                    return getInfoToSwitchByMessageId(Id);
                case USERNAME:
                    return getInfoToSwitchByUsername(username);
            }
            return null;
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("serverConfig").getProperty(Config.class,"server").getProperty("databaseDisconnectError"));
        }
    }

    private Response getInfoToSwitchByUserId(Integer userToBeVisitedId) throws DatabaseDisconnectException {
        User userToBeVisited = Connector.getInstance().fetch(User.class, userToBeVisitedId);
        User user = clientHandler.getUser();
        if (!userToBeVisited.isActive()) {
            logger.info(String.format("user %s wants to check the profile of a user which doesn't exist.",
                    user.getUsername()));
            return new SwitchToProfilePageResponse(SwitchToProfileType.USER,
                    false, false, "", userToBeVisitedId);
        }
        if (user.equals(userToBeVisited))
            return new SwitchToProfilePageResponse(SwitchToProfileType.USER,
                    true, true, "", userToBeVisitedId);

        return new SwitchToProfilePageResponse(SwitchToProfileType.USER,
                true, false, "", userToBeVisitedId);

    }

    private Response getInfoToSwitchByTweetId(Integer tweetId) throws DatabaseDisconnectException {
        Tweet tweet = Connector.getInstance().fetch(Tweet.class, tweetId);
        User userToBeVisited = Connector.getInstance().fetch(User.class, tweet.getWriter());
        User user = clientHandler.getUser();
        if (!userToBeVisited.isActive()) {
            logger.info(String.format("user %s wants to check the profile of a user which doesn't exist.",
                    user.getUsername()));
            return new SwitchToProfilePageResponse(SwitchToProfileType.TWEET,
                    false, false, "", tweet.getWriter());
        }
        if (user.equals(userToBeVisited))
            return new SwitchToProfilePageResponse(SwitchToProfileType.TWEET,
                    true, true, Config.getConfig("serverConfig").getProperty(Config.class,"tweets").getProperty(String.class,
                    "viewSelfProfileError"), tweet.getWriter());

        return new SwitchToProfilePageResponse(SwitchToProfileType.TWEET,
                true, false, "", tweet.getWriter());
    }

    private Response getInfoToSwitchByMessageId(Integer messageId) throws DatabaseDisconnectException {
        Message message = Connector.getInstance().fetch(Message.class, messageId);
        User userToBeVisited = Connector.getInstance().fetch(User.class, message.getWriter());
        User user = clientHandler.getUser();
        if (!userToBeVisited.isActive()) {
            logger.info(String.format("user %s wants to check the profile of a user which doesn't exist.",
                    user.getUsername()));
            return new SwitchToProfilePageResponse(SwitchToProfileType.MESSAGE,
                    false, false, "", message.getWriter());
        }
        if (user.equals(userToBeVisited))
            return new SwitchToProfilePageResponse(SwitchToProfileType.MESSAGE,
                    true, true, Config.getConfig("serverConfig").getProperty(Config.class,"messageViewerPage").getProperty(String.class,
                    "viewSelfProfileError"), message.getWriter());

        return new SwitchToProfilePageResponse(SwitchToProfileType.MESSAGE,
                true, false, "", message.getWriter());
    }

    private Response getInfoToSwitchByUsername(String username) throws DatabaseDisconnectException {
        User userToBeVisited = Connector.getInstance().getUserByUsername(username);
        User user = clientHandler.getUser();
        if(userToBeVisited == null)
            return new SwitchToProfilePageResponse(SwitchToProfileType.USERNAME, false, false,
                    Config.getConfig("serverConfig").getProperty(Config.class,"explorerPage").getProperty("notFoundError"), null);

        if (!userToBeVisited.isActive()) {
            logger.info(String.format("user %s wants to check the profile of a user which doesn't exist.",
                    user.getUsername()));
            return new SwitchToProfilePageResponse(SwitchToProfileType.USER,
                    false, false, "", userToBeVisited.getId());
        }
        if (user.equals(userToBeVisited))
            return new SwitchToProfilePageResponse(SwitchToProfileType.USER,
                    true, true, "", userToBeVisited.getId());

        return new SwitchToProfilePageResponse(SwitchToProfileType.USER,
                true, false, "", userToBeVisited.getId());

    }

    public Response getUpdate(Integer userToBeVisitedId) {
        try {
            User userToBeVisited = Connector.getInstance().fetch(User.class, userToBeVisitedId);
            User user = clientHandler.getUser();

            ImageLoader imageLoader = new ImageLoader();
            BufferedImage bufferedImage = imageLoader.getByID(userToBeVisited.getAvatar());
            ImageUtils imageUtils = new ImageUtils();
            String avatarString = null;
            try {
                avatarString = imageUtils.toString(bufferedImage, "png");
            } catch (IOException e) {
                logger.warn("can't convert buffered image to byte array");
                e.printStackTrace();
            }

            String username = userToBeVisited.getUsername();
            String firstname = userToBeVisited.getFirstname();
            String lastname = userToBeVisited.getLastname();
            String lastSeen = getLastSeen(user, userToBeVisited);
            String bio = userToBeVisited.getBio();
            String birthdate = null;
            String email;
            String phoneNumber;
            if (userToBeVisited.isPublicData()) {
                if (userToBeVisited.getBirthDate() != null)
                    birthdate = userToBeVisited.getBirthDate().toString();
                email = userToBeVisited.getEmail();
                phoneNumber = userToBeVisited.getPhoneNumber();
            } else {
                birthdate = Config.getConfig("serverConfig").getProperty(Config.class,"profilePage").getProperty(String.class, "private");
                email = Config.getConfig("serverConfig").getProperty(Config.class,"profilePage").getProperty(String.class, "private");
                phoneNumber = Config.getConfig("serverConfig").getProperty(Config.class,"profilePage").getProperty(String.class, "private");
            }
            String blockString;
            if (user.getBlockList().contains(userToBeVisited.getId()))
                blockString = Config.getConfig("serverConfig").getProperty(Config.class,"profilePage").getProperty(String.class, "unblockButtonText");
            else
                blockString = Config.getConfig("serverConfig").getProperty(Config.class,"profilePage").getProperty(String.class, "blockButtonText");

            String muteString;
            if (user.getMutedUsers().contains(userToBeVisited.getId()))
                muteString = Config.getConfig("serverConfig").getProperty(Config.class,"profilePage").getProperty(String.class, "unmuteButtonText");
            else
                muteString = Config.getConfig("serverConfig").getProperty(Config.class,"profilePage").getProperty(String.class, "muteButtonText");

            String followString;
            if (user.getFollowings().contains(userToBeVisited.getId()))
                followString = Config.getConfig("serverConfig").getProperty(Config.class,"profilePage").
                        getProperty(String.class, "unfollowButtonText");
            else if (userToBeVisited.getRequests().contains(user.getId()))
                followString = Config.getConfig("serverConfig").getProperty(Config.class,"profilePage").
                        getProperty(String.class, "requestedButtonText");
            else
                followString = Config.getConfig("serverConfig").getProperty(Config.class,"profilePage").
                        getProperty(String.class, "followButtonText");
            return new UpdateProfilePageResponse(username, avatarString, firstname, lastname, lastSeen, bio, birthdate
                    , email, phoneNumber, blockString, muteString, followString);
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("serverConfig").getProperty(Config.class,"server").getProperty("databaseDisconnectError"));
        }
    }

    private String getLastSeen(User user, User userToBeVisited) {

        if (userToBeVisited.getLastSeenType().equals(Config.getConfig("serverConfig").getProperty(Config.class,"profilePage").
                getProperty(String.class, "nobodyLastSeen")))
            return Config.getConfig("serverConfig").getProperty(Config.class,"profilePage").getProperty(String.class, "recently");

        if (userToBeVisited.getLastSeenType().equals(Config.getConfig("serverConfig").getProperty(Config.class,"profilePage").
                getProperty(String.class, "followingsLastSeen")) &&
                !userToBeVisited.getFollowings().contains(user.getId()))
            return Config.getConfig("serverConfig").getProperty(Config.class,"profilePage").getProperty(String.class, "recently");

        if (userToBeVisited.getLastSeen() == null)
            return Config.getConfig("serverConfig").getProperty(Config.class,"profilePage").getProperty(String.class, "online");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return userToBeVisited.getLastSeen().format(formatter);
    }

    public Response profileHandle(ProfilePageEventType profilePageEventType, Integer userToBeVisited) {
        switch (profilePageEventType) {
            case MUTE:
                return muteHandling(userToBeVisited);
            case BLOCK:
                return blockHandling(userToBeVisited);
            case FOLLOW:
                return followHandling(userToBeVisited);
        }
        return null;
    }

    public Response muteHandling(Integer userToBeVisitedID) {
        try {
            User user = clientHandler.getUser();
            User userToBeVisited = Connector.getInstance().fetch(User.class, userToBeVisitedID);

            if (user.getMutedUsers().contains(userToBeVisited.getId())) {
                user.removeFromMutedUsers(userToBeVisited.getId());
                Connector.getInstance().save(user);
                logger.info(String.format("user %s unmuted user %s.", user.getUsername(), userToBeVisited.getUsername()));
            } else {
                user.addToMutedUsers(userToBeVisited.getId());
                Connector.getInstance().save(user);
                logger.info(String.format("user %s muted user %s.", user.getUsername(), userToBeVisited.getUsername()));
            }
            return null;
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("serverConfig").getProperty(Config.class,"server").getProperty("databaseDisconnectError"));
        }
    }

    public Response blockHandling(Integer userToBeVisitedID) {
        try {
            User user = clientHandler.getUser();
            User userToBeVisited = Connector.getInstance().fetch(User.class, userToBeVisitedID);

            if (user.getBlockList().contains(userToBeVisited.getId())) {
                user.removeFromBlocklist(userToBeVisited.getId());
                Connector.getInstance().save(user);
                logger.info(String.format("user %s unblocked user %s.", user.getUsername(), userToBeVisited.getUsername()));
            } else {
                user.addToBlocklist(userToBeVisited.getId());
                Connector.getInstance().save(user);
                logger.info(String.format("user %s blocked user %s.", user.getUsername(), userToBeVisited.getUsername()));
            }
            return null;
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("serverConfig").getProperty(Config.class,"server").getProperty("databaseDisconnectError"));
        }
    }

    public Response followHandling(Integer userToBeVisitedID) {
        try {
            User user = clientHandler.getUser();
            User userToBeVisited = Connector.getInstance().fetch(User.class, userToBeVisitedID);

            if (user.getFollowings().contains(userToBeVisited.getId())) {
                user.removeFromFollowings(userToBeVisited.getId());
                userToBeVisited.removeFromFollowers(user.getId());
                userToBeVisited.addToNotifications(String.format("user %s unfollowed you!", user.getUsername()));
                Connector.getInstance().save(user);
                Connector.getInstance().save(userToBeVisited);
                logger.info(String.format("user %s unfollowed user %s.", user.getUsername(), userToBeVisited.getUsername()));
            } else if (userToBeVisited.getRequests().contains(user.getId())) {
                logger.info(String.format("user %s removed the request to user %s.", user.getUsername(),
                        userToBeVisited.getUsername()));
                userToBeVisited.removeFromRequests(user.getId());
                Connector.getInstance().save(userToBeVisited);
            } else if (userToBeVisited.isPrivate()) {
                userToBeVisited.addToRequests(user.getId());
                Connector.getInstance().save(userToBeVisited);
                logger.info(String.format("user %s requested user %s.", user.getUsername(), userToBeVisited.getUsername()));
            } else {
                user.addToFollowings(userToBeVisited.getId());
                userToBeVisited.addToFollowers(user.getId());
                userToBeVisited.addToNotifications(String.format("user %s followed you!", user.getUsername()));
                Connector.getInstance().save(user);
                Connector.getInstance().save(userToBeVisited);
                logger.info(String.format("user %s followed user %s.", user.getUsername(), userToBeVisited.getUsername()));
            }
            return null;
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("serverConfig").getProperty(Config.class,"server").getProperty("databaseDisconnectError"));
        }
    }
}
