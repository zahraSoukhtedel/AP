package ir.sharif.math.zahraSoukhtedel.models.viewModels;

import lombok.Getter;

import java.time.LocalDateTime;

public class ViewTweet {
    @Getter
    private final String retweetString;
    @Getter
    private final String tweetContent;
    @Getter
    private final LocalDateTime tweetDateTime;
    @Getter
    private final Integer tweetId;
    @Getter
    private final boolean myTweets;

    public ViewTweet(String retweetString, String tweetContent, LocalDateTime tweetDateTime, Integer tweetId, boolean myTweets) {
        this.retweetString = retweetString;
        this.tweetContent = tweetContent;
        this.tweetDateTime = tweetDateTime;
        this.tweetId = tweetId;
        this.myTweets = myTweets;
    }
}
