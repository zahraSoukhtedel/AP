package ir.sharif.math.zahraSoukhtedel.controller.messagingPage.chatGroupPage;

import ir.sharif.math.zahraSoukhtedel.view.ViewManager;
import ir.sharif.math.zahraSoukhtedel.view.messagingPage.ChatGroupPage.ChatGroupFXController;
import javafx.application.Platform;

public class ChatGroupController {

    public void applyError(String error) {
        if(!(ViewManager.getInstance().getCurPage().getFxController() instanceof ChatGroupFXController))
            return;
        ChatGroupFXController chatGroupFXController = (ChatGroupFXController)
                ViewManager.getInstance().getCurPage().getFxController();
        Platform.runLater(() -> chatGroupFXController.setErrorLabel(error));
    }
}
