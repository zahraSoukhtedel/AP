package ir.sharif.math.zahraSoukhtedel.controller.personalPage.listsPage;

import ir.sharif.math.zahraSoukhtedel.controller.ClientHandler;
import ir.sharif.math.zahraSoukhtedel.exceptions.DatabaseDisconnectException;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.database.Connector;
import ir.sharif.math.zahraSoukhtedel.models.Group;
import ir.sharif.math.zahraSoukhtedel.models.User;
import ir.sharif.math.zahraSoukhtedel.models.viewModels.ViewGroup;
import ir.sharif.math.zahraSoukhtedel.models.viewModels.ViewUser;
import ir.sharif.math.zahraSoukhtedel.response.ShowErrorResponse;
import ir.sharif.math.zahraSoukhtedel.response.personalPage.listsPage.UpdateListsPageResponse;
import ir.sharif.math.zahraSoukhtedel.util.Config;

import java.util.ArrayList;
import java.util.List;

public class ListsPageController {
    private final ClientHandler clientHandler;

    public ListsPageController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public Response getUpdate() {
        User user = clientHandler.getUser();
        try {
            return new UpdateListsPageResponse(getUserList(user.getFollowers()), getUserList(user.getFollowings()),
                    getUserList(user.getBlockList()), getGroupList(user.getGroups()));
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("serverConfig").getProperty(Config.class,"server").getProperty("databaseDisconnectError"));
        }
    }

    private List<ViewUser> getUserList(List<Integer> idList) throws DatabaseDisconnectException {
        List<ViewUser> userList = new ArrayList<>();
        for (Integer id : idList) {
            User currentUser = Connector.getInstance().fetch(User.class, id);
            userList.add(new ViewUser(currentUser.getUsername(), currentUser.getId(), currentUser.isActive()));
        }
        return userList;
    }

    private List<ViewGroup> getGroupList(List<Integer> idList) throws DatabaseDisconnectException {
        List<ViewGroup> groupList = new ArrayList<>();
        for (Integer id : idList) {
            Group currentGroup = Connector.getInstance().fetch(Group.class, id);
            groupList.add(new ViewGroup(currentGroup.getGroupName(), currentGroup.getId()));
        }
        return groupList;
    }
}
