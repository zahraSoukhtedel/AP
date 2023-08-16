package ir.sharif.math.zahraSoukhtedel.response.tweets;

import ir.sharif.math.zahraSoukhtedel.models.viewModels.ViewTweet;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.response.ResponseVisitor;

import java.util.List;

public class UpdateTweetPageResponse extends Response {
    private final String tweetContent;
    private final String tweetDate;
    private final String retweetString;
    private final String tweetImage;
    private final int likeNumbers;
    private final List<ViewTweet> viewTweetList;
    private final String likeButtonText;

    public UpdateTweetPageResponse(String tweetContent, String tweetDate, String retweetString, String tweetImage,
                                   int likeNumbers, List<ViewTweet> viewTweetList, String likeButtonText) {
        this.tweetContent = tweetContent;
        this.tweetDate = tweetDate;
        this.retweetString = retweetString;
        this.tweetImage = tweetImage;
        this.likeNumbers = likeNumbers;
        this.viewTweetList = viewTweetList;
        this.likeButtonText = likeButtonText;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.updateTweetPage(tweetContent, tweetDate, retweetString, tweetImage, likeNumbers, viewTweetList,
                likeButtonText);
    }

}
