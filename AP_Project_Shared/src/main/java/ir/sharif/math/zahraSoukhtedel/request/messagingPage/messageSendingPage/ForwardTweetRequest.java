package ir.sharif.math.zahraSoukhtedel.request.messagingPage.messageSendingPage;

import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.request.RequestVisitor;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import lombok.ToString;

@ToString
public class ForwardTweetRequest extends Request {
    final Integer tweetId;

    public ForwardTweetRequest(Integer tweetId) {
        this.tweetId = tweetId;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.forwardTweet(tweetId);
    }
}
