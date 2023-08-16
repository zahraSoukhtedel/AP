package ir.sharif.math.zahraSoukhtedel.response.tweets;

import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.response.ResponseVisitor;

public class TweetActionResponse extends Response {
    private final String verdict;
    private final boolean isError;

    public TweetActionResponse(String verdict, boolean isError) {
        this.verdict = verdict;
        this.isError = isError;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.applyTweetActionResponse(verdict, isError);
    }
}
