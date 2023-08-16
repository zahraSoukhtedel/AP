package ir.sharif.math.zahraSoukhtedel.controller.messagingPage.messageSendingPage;

import ir.sharif.math.zahraSoukhtedel.listeners.messagingPage.messageSendigPage.ChatPanelToChatPageListener;
import ir.sharif.math.zahraSoukhtedel.listeners.messagingPage.messageSendigPage.GroupPanelToChatPageListener;
import ir.sharif.math.zahraSoukhtedel.models.viewModels.ViewChat;
import ir.sharif.math.zahraSoukhtedel.models.viewModels.ViewGroup;
import ir.sharif.math.zahraSoukhtedel.view.Page;
import ir.sharif.math.zahraSoukhtedel.view.ViewManager;
import ir.sharif.math.zahraSoukhtedel.view.messagingPage.messageSendingPage.ChatSelectingFXController;
import ir.sharif.math.zahraSoukhtedel.view.messagingPage.messageSendingPage.ChatSelectingPanelFXController;
import ir.sharif.math.zahraSoukhtedel.view.messagingPage.messageSendingPage.GroupSelectingPanelFXController;
import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class ChatSelectingController {

    public void refresh(List<ViewGroup> groups, List<ViewChat> chats) {
        if(!((ViewManager.getInstance().getCurPage().getFxController()) instanceof ChatSelectingFXController))
            return;
        ChatSelectingFXController chatSelectingFXController = (ChatSelectingFXController)
                ViewManager.getInstance().getCurPage().getFxController();
        Platform.runLater(() -> {
            chatSelectingFXController.clear();

            //adding groups
            for (ViewGroup group : groups) {
                Page groupSelectingPanel = new Page("groupSelectingPanel");
                GroupSelectingPanelFXController groupSelectingPanelFXController = (GroupSelectingPanelFXController)
                        groupSelectingPanel.getFxController();
                groupSelectingPanelFXController.setGroup(group.getGroupId());
                groupSelectingPanelFXController.setGroupPanelToChatPageListener(new
                        GroupPanelToChatPageListener(chatSelectingFXController));
                groupSelectingPanelFXController.setGroupNameLabel(group.getGroupName());
                chatSelectingFXController.getGroupsBox().getChildren().add(
                        new AnchorPane(groupSelectingPanelFXController.getGroupSelectingPanel()));
            }
            //adding chats
            for (ViewChat chat : chats) {
                Page chatSelectingPanel = new Page("chatSelectingPanel");
                ChatSelectingPanelFXController chatSelectingPanelFXController = (ChatSelectingPanelFXController)
                        chatSelectingPanel.getFxController();
                chatSelectingPanelFXController.setChatID(chat.getChatId());
                chatSelectingPanelFXController.setChatPanelToChatPageListener(new
                        ChatPanelToChatPageListener(chatSelectingFXController));
                chatSelectingPanelFXController.setChatNameLabel(chat.getChatName());
                chatSelectingFXController.getChatsBox().getChildren().add(
                        new AnchorPane(chatSelectingPanelFXController.getChatSelectingPanel()));
            }
        });
    }
}
