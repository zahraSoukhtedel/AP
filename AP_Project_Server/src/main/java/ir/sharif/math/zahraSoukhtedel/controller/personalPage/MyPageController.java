package ir.sharif.math.zahraSoukhtedel.controller.personalPage;

import ir.sharif.math.zahraSoukhtedel.controller.ClientHandler;
import ir.sharif.math.zahraSoukhtedel.controller.tweets.TweetManager;
import ir.sharif.math.zahraSoukhtedel.database.Connector;
import ir.sharif.math.zahraSoukhtedel.database.ImageLoader;
import ir.sharif.math.zahraSoukhtedel.exceptions.DatabaseDisconnectException;
import ir.sharif.math.zahraSoukhtedel.models.User;
import ir.sharif.math.zahraSoukhtedel.models.media.Tweet;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.response.ShowErrorResponse;
import ir.sharif.math.zahraSoukhtedel.response.personalPage.UpdatePersonalPageResponse;
import ir.sharif.math.zahraSoukhtedel.models.viewModels.ViewTweet;
import ir.sharif.math.zahraSoukhtedel.util.Config;
import ir.sharif.math.zahraSoukhtedel.util.ImageUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyPageController {

    static private final Logger logger = LogManager.getLogger(MyPageController.class);

    private final ClientHandler clientHandler;

    public MyPageController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public Response getUpdate() {
        User user =  clientHandler.getUser();

        ImageLoader imageLoader = new ImageLoader();
        BufferedImage bufferedImage = imageLoader.getByID(user.getAvatar());

        ImageUtils imageUtils = new ImageUtils();
        try {
            return new UpdatePersonalPageResponse(imageUtils.toString(bufferedImage, "png"), getViewTweets(user));
        } catch (IOException e) {
            logger.warn("can't convert buffered image to byte array");
            e.printStackTrace();
            return null;
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("serverConfig").getProperty(Config.class,"server").getProperty("databaseDisconnectError"));
        }
    }

    private List<ViewTweet> getViewTweets(User user) throws DatabaseDisconnectException {
        List<Tweet> tweets = new ArrayList<>();
        for (Integer tweetID : user.getTweets()) {
            Tweet tweet = Connector.getInstance().fetch(Tweet.class, tweetID);
            if(tweet.getSpamReports() > Config.getConfig("serverConfig").getProperty(Config.class,"tweets").getProperty(Integer.class, "maxSpam")) //spam
                continue;
            tweets.add(tweet);
        }
        Tweet.sortByDateTime(tweets);
        return TweetManager.getInstance().getViewTweets(user, tweets, true);
    }
}
