package ir.sharif.math.zahraSoukhtedel.request.tweets;

import ir.sharif.math.zahraSoukhtedel.models.events.TweetPageEventType;
import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.request.RequestVisitor;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import lombok.ToString;

@ToString
public class TweetActionRequest extends Request {
    private final TweetPageEventType tweetPageEventType;
    private final Integer tweetId;

    public TweetActionRequest(TweetPageEventType tweetPageEventType, Integer tweetId) {
        this.tweetPageEventType = tweetPageEventType;
        this.tweetId = tweetId;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.applyTweetAction(tweetPageEventType, tweetId);
    }
}
