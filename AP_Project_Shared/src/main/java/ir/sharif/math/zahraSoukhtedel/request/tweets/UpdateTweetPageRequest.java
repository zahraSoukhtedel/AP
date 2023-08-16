package ir.sharif.math.zahraSoukhtedel.request.tweets;

import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.request.RequestVisitor;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import lombok.ToString;

@ToString
public class UpdateTweetPageRequest extends Request {
    private final Integer tweetId;
    private final boolean myTweets;

    public UpdateTweetPageRequest(Integer tweetId, boolean myTweets) {
        this.tweetId = tweetId;
        this.myTweets = myTweets;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.updateTweetPage(tweetId, myTweets);
    }
}
