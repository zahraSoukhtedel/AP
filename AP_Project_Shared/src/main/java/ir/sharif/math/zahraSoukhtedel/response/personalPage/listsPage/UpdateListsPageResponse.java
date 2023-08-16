package ir.sharif.math.zahraSoukhtedel.response.personalPage.listsPage;

import ir.sharif.math.zahraSoukhtedel.models.viewModels.ViewUser;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.response.ResponseVisitor;
import ir.sharif.math.zahraSoukhtedel.models.viewModels.ViewGroup;

import java.util.List;

public class UpdateListsPageResponse extends Response {
    private final List<ViewUser> followers;
    private final List<ViewUser> followings;
    private final List<ViewUser> blocklist;
    private final List<ViewGroup> groups;

    public UpdateListsPageResponse(List<ViewUser> followers, List<ViewUser> followings, List<ViewUser> blocklist,
                                   List<ViewGroup> groups) {
        this.followers = followers;
        this.followings = followings;
        this.blocklist = blocklist;
        this.groups = groups;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.updateListsPage(followers, followings, blocklist, groups);
    }
}
