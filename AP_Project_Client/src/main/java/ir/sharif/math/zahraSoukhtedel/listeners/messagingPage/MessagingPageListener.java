package ir.sharif.math.zahraSoukhtedel.listeners.messagingPage;

import ir.sharif.math.zahraSoukhtedel.view.Page;
import ir.sharif.math.zahraSoukhtedel.view.ViewManager;
import ir.sharif.math.zahraSoukhtedel.view.messagingPage.messageSendingPage.MessageSendingFXController;

public class MessagingPageListener {

    public void stringEventOccurred(String event) {
        switch (event) {
            case "sendMessage":
                Page page = new Page("messageSendingPage");
                MessageSendingFXController messageSendingFXController = (MessageSendingFXController) page.getFxController();
                messageSendingFXController.setReceiverID(null);
                ViewManager.getInstance().setPage(page);
                break;
            case "chatGroup":
                ViewManager.getInstance().setPage(new Page("chatGroupPage"));
                break;
            default:
                break;
        }
    }
}
