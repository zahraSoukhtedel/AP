package ir.sharif.math.zahraSoukhtedel.listeners.messagingPage.messageSendigPage;

import ir.sharif.math.zahraSoukhtedel.controller.Client;
import ir.sharif.math.zahraSoukhtedel.controller.messagingPage.messageSendingPage.ChatSelectingController;
import ir.sharif.math.zahraSoukhtedel.model.ChatOffline;
import ir.sharif.math.zahraSoukhtedel.model.GroupOffline;
import ir.sharif.math.zahraSoukhtedel.model.MessageOffline;
import ir.sharif.math.zahraSoukhtedel.models.viewModels.ViewChat;
import ir.sharif.math.zahraSoukhtedel.models.viewModels.ViewGroup;
import ir.sharif.math.zahraSoukhtedel.models.viewModels.ViewMessage;
import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.request.messagingPage.messageSendingPage.NewMessageRequest;
import ir.sharif.math.zahraSoukhtedel.util.ImageUtils;
import ir.sharif.math.zahraSoukhtedel.view.Page;
import ir.sharif.math.zahraSoukhtedel.view.ViewManager;
import ir.sharif.math.zahraSoukhtedel.view.messagingPage.messageSendingPage.ChatSelectingFXController;
import javafx.application.Platform;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NewMessageListener {
    static private final Logger logger = LogManager.getLogger(NewMessageListener.class);

    public void send(BufferedImage messageImage, String messageContent, Integer receiverID, LocalDateTime dateTime, boolean isTiming) {

        ImageUtils imageUtils = new ImageUtils();
        try {
            String avatarString = imageUtils.toString(messageImage, "png");
            Request request = new NewMessageRequest(avatarString, messageContent, receiverID, dateTime, isTiming);
            logger.info(String.format("client requested %s", request));


            if (!Client.isOnline()) {
                int s = Client.getUserOffline().getNewMessages().size();
                ViewMessage viewMessage = new ViewMessage(s + 10000, messageContent, LocalDateTime.now(), "1", Client.getUserOffline().getUsername());
                MessageOffline messageOffline = new MessageOffline(viewMessage);
                try {
                    messageOffline.setImageAddress(imageUtils.toString(messageImage, "png"));
                    Client.getUserOffline().getNewMessages().add(messageOffline);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                List<ViewChat> chats = new ArrayList<>();
                List<ViewGroup> groups = new ArrayList<>();
                for (ChatOffline chatOffline : Client.getUserOffline().getLoadedChats()) {
                    chats.add(chatOffline.getViewChat());
                }
                for (GroupOffline g : Client.getUserOffline().getLoadedGroups()) {
                    groups.add(g.getViewGroup());
                }


                Platform.runLater(() -> {
                    Page page = new Page("chatSelectingPage");
                    ChatSelectingFXController chatSelectingFXController = (ChatSelectingFXController) page.getFxController();
                    chatSelectingFXController.setMessageID(s + 10000);
                    ViewManager.getInstance().setPage(page);
                    ChatSelectingController chatSelectingController = new ChatSelectingController();
                    chatSelectingController.refresh(groups, chats);

                });
                List<Request> l = new ArrayList<>();
                l.add(request);
                Client.getMessageRequests().put(s + 10000, l);
            } else {
                Client.getClient().addRequest(request);
            }

        } catch (IOException e) {
            e.printStackTrace();
            logger.warn("can't convert buffered image to byte array");
        }
    }
}
