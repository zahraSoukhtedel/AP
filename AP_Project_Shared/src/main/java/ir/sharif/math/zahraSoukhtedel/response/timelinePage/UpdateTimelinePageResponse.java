package ir.sharif.math.zahraSoukhtedel.response.timelinePage;

import ir.sharif.math.zahraSoukhtedel.models.viewModels.ViewTweet;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.response.ResponseVisitor;

import java.util.List;

public class UpdateTimelinePageResponse extends Response {
    private final List<ViewTweet> viewTweetList;

    public UpdateTimelinePageResponse(List<ViewTweet> viewTweetList) {
        this.viewTweetList = viewTweetList;
    }


    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.updateTimelinePage(viewTweetList);
    }
}
