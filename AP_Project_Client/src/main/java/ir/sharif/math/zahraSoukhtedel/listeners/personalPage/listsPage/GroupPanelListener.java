package ir.sharif.math.zahraSoukhtedel.listeners.personalPage.listsPage;

import ir.sharif.math.zahraSoukhtedel.view.Page;
import ir.sharif.math.zahraSoukhtedel.view.ViewManager;
import ir.sharif.math.zahraSoukhtedel.view.personalPage.listsPage.GroupFXController;

public class GroupPanelListener {

    public void viewEventOccurred(Integer group) {
        ViewManager.getInstance().setPage(new Page("groupPage"));
        GroupFXController groupFXController = (GroupFXController) ViewManager.getInstance().getCurPage().getFxController();
        groupFXController.setGroup(group);
    }
}
