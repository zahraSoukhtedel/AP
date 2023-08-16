package ir.sharif.math.zahraSoukhtedel.listeners.messagingPage;


import ir.sharif.math.zahraSoukhtedel.view.Page;
import ir.sharif.math.zahraSoukhtedel.view.ViewManager;
import ir.sharif.math.zahraSoukhtedel.view.messagingPage.MessageViewerFXController;

public class MessagePanelListener {

    public void view(Integer messageId) {
        Page page = new Page("messageViewerPage");
        MessageViewerFXController messageViewerFXController = (MessageViewerFXController) page.getFxController();
        messageViewerFXController.setMessageID(messageId);
        ViewManager.getInstance().setPage(page);
    }
}
