package ir.sharif.math.zahraSoukhtedel.controller.personalPage.notificationsPage;

import ir.sharif.math.zahraSoukhtedel.models.viewModels.ViewUser;
import ir.sharif.math.zahraSoukhtedel.view.Page;
import ir.sharif.math.zahraSoukhtedel.view.ViewManager;
import ir.sharif.math.zahraSoukhtedel.view.personalPage.notificationsPage.NotificationsFXController;
import ir.sharif.math.zahraSoukhtedel.view.personalPage.notificationsPage.RequestFXController;
import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class NotificationsPageController {
    public void refresh(List<String> requestMessages, List<String> systemMessages, List<ViewUser> requests) {
        Platform.runLater(() -> {
            if (!(ViewManager.getInstance().getCurPage().getFxController() instanceof NotificationsFXController))
                return;
            NotificationsFXController notificationsFXController = (NotificationsFXController)
                    ViewManager.getInstance().getCurPage().getFxController();
            notificationsFXController.clear();
            //add request messages
            for (String requestMessage : requestMessages)
                notificationsFXController.addRequestMessage(requestMessage);
            //add system messages
            for (String systemMessage : systemMessages)
                notificationsFXController.addSystemMessage(systemMessage);
            //add requests
            for (ViewUser requester : requests) {
                Page requestPanel = new Page("requestPanel");
                RequestFXController requestFXController = (RequestFXController) requestPanel.getFxController();
                requestFXController.setRequesterID(requester.getUserId());
                requestFXController.setRequester(requester.getUsername());
                notificationsFXController.getRequests().getChildren().add(new AnchorPane(requestFXController.getRequestPane()));
            }
        });
    }
}
