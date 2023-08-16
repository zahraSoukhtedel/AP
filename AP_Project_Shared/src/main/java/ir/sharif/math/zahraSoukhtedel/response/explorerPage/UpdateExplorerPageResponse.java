package ir.sharif.math.zahraSoukhtedel.response.explorerPage;

import ir.sharif.math.zahraSoukhtedel.models.viewModels.ViewTweet;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.response.ResponseVisitor;

import java.util.List;

public class UpdateExplorerPageResponse extends Response {

    private final List<ViewTweet> viewTweetList;

    public UpdateExplorerPageResponse(List<ViewTweet> viewTweetList) {
        this.viewTweetList = viewTweetList;
    }


    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.updateExplorerPage(viewTweetList);
    }
}
