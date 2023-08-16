package ir.sharif.math.zahraSoukhtedel.controller.tweets;

import ir.sharif.math.zahraSoukhtedel.controller.ClientHandler;
import ir.sharif.math.zahraSoukhtedel.database.Connector;
import ir.sharif.math.zahraSoukhtedel.database.ImageLoader;
import ir.sharif.math.zahraSoukhtedel.exceptions.DatabaseDisconnectException;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.response.ShowErrorResponse;
import ir.sharif.math.zahraSoukhtedel.models.User;
import ir.sharif.math.zahraSoukhtedel.models.media.Tweet;
import ir.sharif.math.zahraSoukhtedel.response.BackResponse;
import ir.sharif.math.zahraSoukhtedel.util.Config;
import ir.sharif.math.zahraSoukhtedel.util.ImageUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class NewTweetController {
    static private final Logger logger = LogManager.getLogger(NewTweetController.class);

    private final ClientHandler clientHandler;

    public NewTweetController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public Response addTweet(String content, String avatarString, Integer upPost) {
        try {
            User user = clientHandler.getUser();
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
            Tweet curTweet = new Tweet(content, user.getId(), upPost, imageID);
            Connector.getInstance().save(curTweet);

            user.addToTweets(curTweet.getId());
            Connector.getInstance().save(user);
            if (upPost != null) {
                Tweet upTweet = Connector.getInstance().fetch(Tweet.class, upPost);
                upTweet.addComment(curTweet.getId());
                Connector.getInstance().save(upTweet);
            }
            logger.info(String.format("user %s added tweet %s", user.getUsername(), curTweet.getId()));
            return new BackResponse();
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("serverConfig").getProperty(Config.class,"server").getProperty("databaseDisconnectError"));
        }
    }
}
