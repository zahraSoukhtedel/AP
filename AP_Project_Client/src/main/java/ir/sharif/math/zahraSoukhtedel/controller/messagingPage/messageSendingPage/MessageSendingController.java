package ir.sharif.math.zahraSoukhtedel.controller.messagingPage.messageSendingPage;

import ir.sharif.math.zahraSoukhtedel.models.viewModels.ViewChat;
import ir.sharif.math.zahraSoukhtedel.models.viewModels.ViewGroup;
import ir.sharif.math.zahraSoukhtedel.view.Page;
import ir.sharif.math.zahraSoukhtedel.view.ViewManager;
import ir.sharif.math.zahraSoukhtedel.view.messagingPage.messageSendingPage.ChatSelectingFXController;
import ir.sharif.math.zahraSoukhtedel.view.messagingPage.messageSendingPage.MessageSendingFXController;
import javafx.application.Platform;

import java.util.List;

public class MessageSendingController {
    public void applyNewMessageResponse(String error, Integer messageId, List<ViewGroup> groups, List<ViewChat> chats) {
        Platform.runLater(() -> {
            if(!error.equals("")) {
                if(!((ViewManager.getInstance().getCurPage().getFxController()) instanceof MessageSendingFXController))
                    return;
                MessageSendingFXController messageSendingFXController = (MessageSendingFXController)
                        ViewManager.getInstance().getCurPage().getFxController();
                messageSendingFXController.setErrorLabel(error);
            }
            else {
                Page page = new Page("chatSelectingPage");
                ChatSelectingFXController chatSelectingFXController = (ChatSelectingFXController) page.getFxController();
                chatSelectingFXController.setMessageID(messageId);
                ViewManager.getInstance().setPage(page);
                ChatSelectingController chatSelectingController = new ChatSelectingController();
                chatSelectingController.refresh(groups, chats);
            }
        });
    }
}
