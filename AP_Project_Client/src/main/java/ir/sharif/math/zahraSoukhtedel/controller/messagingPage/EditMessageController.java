package ir.sharif.math.zahraSoukhtedel.controller.messagingPage;

import ir.sharif.math.zahraSoukhtedel.view.ViewManager;
import ir.sharif.math.zahraSoukhtedel.view.messagingPage.EditMessageFXController;
import javafx.application.Platform;

public class EditMessageController {
    public void applyEditMessageResponse(String error) {
        if(!(ViewManager.getInstance().getCurPage().getFxController() instanceof EditMessageFXController))
            return;
        EditMessageFXController editMessageFXController = (EditMessageFXController)
                ViewManager.getInstance().getCurPage().getFxController();
        Platform.runLater(() -> editMessageFXController.setErrorLabel(error));
    }
}
