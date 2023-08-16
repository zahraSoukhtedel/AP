package ir.sharif.math.zahraSoukhtedel.controller.explorerPage;

import ir.sharif.math.zahraSoukhtedel.controller.ClientHandler;
import ir.sharif.math.zahraSoukhtedel.controller.tweets.TweetManager;
import ir.sharif.math.zahraSoukhtedel.database.Connector;
import ir.sharif.math.zahraSoukhtedel.exceptions.DatabaseDisconnectException;
import ir.sharif.math.zahraSoukhtedel.models.User;
import ir.sharif.math.zahraSoukhtedel.models.media.Tweet;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.response.ShowErrorResponse;
import ir.sharif.math.zahraSoukhtedel.response.explorerPage.UpdateExplorerPageResponse;
import ir.sharif.math.zahraSoukhtedel.util.Config;

import java.util.ArrayList;
import java.util.List;

public class ExplorerController {
    private final ClientHandler clientHandler;

    public ExplorerController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public Response getUpdate() {
        try {
            User user = clientHandler.getUser();
            //getting valid tweets
            List<Tweet> tweets = Connector.getInstance().fetchAll(Tweet.class);
            List<Tweet> publicTweets = new ArrayList<>();
            for (Tweet tweet : tweets)
                if (!Connector.getInstance().fetch(User.class, tweet.getWriter()).isPrivate())
                    publicTweets.add(tweet);

            List<Tweet> validatedTweets = TweetManager.getInstance().validation(user, publicTweets, true);
            Tweet.sortByLikeNumbers(validatedTweets);
            return new UpdateExplorerPageResponse(TweetManager.getInstance().getViewTweets(user, validatedTweets, false));
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("serverConfig").getProperty(Config.class,"server").getProperty("databaseDisconnectError"));
        }
    }
}
