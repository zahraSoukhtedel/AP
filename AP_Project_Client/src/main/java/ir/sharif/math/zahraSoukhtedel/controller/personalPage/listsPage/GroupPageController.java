package ir.sharif.math.zahraSoukhtedel.controller.personalPage.listsPage;

import ir.sharif.math.zahraSoukhtedel.models.viewModels.ViewUser;
import ir.sharif.math.zahraSoukhtedel.view.ViewManager;
import ir.sharif.math.zahraSoukhtedel.view.personalPage.listsPage.GroupFXController;
import javafx.application.Platform;

import java.util.List;

public class GroupPageController {

    public void refresh(List<ViewUser> members) {
        //**************************************************************************************
        if(!(ViewManager.getInstance().getCurPage().getFxController() instanceof GroupFXController))
            return;
        GroupFXController groupFXController = (GroupFXController) ViewManager.getInstance().getCurPage().getFxController();
        Platform.runLater(() -> {
            groupFXController.clear();
            ListsPageController listsPageController = new ListsPageController();
            listsPageController.addListToBox(members, groupFXController.getMembersBox());
        });
    }

    public void applyEditGroupResponse(String error) {
        if(!(ViewManager.getInstance().getCurPage().getFxController() instanceof GroupFXController))
            return;
        GroupFXController groupFXController = (GroupFXController) ViewManager.getInstance().
                getCurPage().getFxController();
        Platform.runLater(() -> {
            if(error.equals(""))
                ViewManager.getInstance().back();
            else
                groupFXController.setErrorLabel(error);
        });
    }
}
