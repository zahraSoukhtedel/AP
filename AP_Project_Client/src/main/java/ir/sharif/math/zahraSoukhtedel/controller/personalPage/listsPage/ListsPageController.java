package ir.sharif.math.zahraSoukhtedel.controller.personalPage.listsPage;

import ir.sharif.math.zahraSoukhtedel.models.viewModels.ViewGroup;
import ir.sharif.math.zahraSoukhtedel.models.viewModels.ViewUser;
import ir.sharif.math.zahraSoukhtedel.view.Page;
import ir.sharif.math.zahraSoukhtedel.view.ViewManager;
import ir.sharif.math.zahraSoukhtedel.view.personalPage.listsPage.GroupPanelFXController;
import ir.sharif.math.zahraSoukhtedel.view.personalPage.listsPage.ListsFXController;
import ir.sharif.math.zahraSoukhtedel.view.personalPage.listsPage.ViewPanelFXController;
import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class ListsPageController {
    public void refresh(List<ViewUser> followers, List<ViewUser> followings, List<ViewUser> blocklist, List<ViewGroup> groups) {
        if (!(ViewManager.getInstance().getCurPage().getFxController() instanceof ListsFXController))
            return;
        ListsFXController listsFXController = (ListsFXController) ViewManager.getInstance().getCurPage().getFxController();
        Platform.runLater(() -> {
            listsFXController.clear();
            addListToBox(followers, listsFXController.getFollowersBox());
            addListToBox(followings, listsFXController.getFollowingsBox());
            addListToBox(blocklist, listsFXController.getBlockedUsersBox());

            //adding groups to group box
            for (ViewGroup group : groups) {
                Page groupPanel = new Page("groupPanel");
                GroupPanelFXController groupPanelFXController = (GroupPanelFXController) groupPanel.getFxController();
                groupPanelFXController.setGroup(group.getGroupId());
                groupPanelFXController.setGroupNameLabel(group.getGroupName());
                listsFXController.getGroupsBox().getChildren().add(new AnchorPane(groupPanelFXController.getGroupPane()));
            }
        });
    }

    public void addListToBox(List<ViewUser> userList, VBox box) {
        List<ViewUser> activeUsers = new ArrayList<>();
        for (ViewUser currentUser : userList) {
            if (currentUser.isActive())
                activeUsers.add(currentUser);
        }
        for (ViewUser userToBeVisited : activeUsers) {
            Page viewPanel = new Page("viewPanel");
            ViewPanelFXController viewPanelFXController = (ViewPanelFXController) viewPanel.getFxController();
            viewPanelFXController.setUserToBeVisitedID(userToBeVisited.getUserId());
            viewPanelFXController.setUsernameLabel(userToBeVisited.getUsername());
            box.getChildren().add(new AnchorPane(viewPanelFXController.getViewPane()));
        }
    }
}
