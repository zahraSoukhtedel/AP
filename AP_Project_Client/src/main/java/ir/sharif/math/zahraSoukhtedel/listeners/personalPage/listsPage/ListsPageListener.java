package ir.sharif.math.zahraSoukhtedel.listeners.personalPage.listsPage;

import ir.sharif.math.zahraSoukhtedel.view.Page;
import ir.sharif.math.zahraSoukhtedel.view.ViewManager;

public class ListsPageListener {

    public void stringEventOccurred(String event) {
        if ("newGroup".equals(event))
            ViewManager.getInstance().setPage(new Page("newGroupPage"));
    }
}
