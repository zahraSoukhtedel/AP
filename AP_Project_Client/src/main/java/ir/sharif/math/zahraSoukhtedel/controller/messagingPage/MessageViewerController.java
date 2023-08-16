package ir.sharif.math.zahraSoukhtedel.controller.messagingPage;

import ir.sharif.math.zahraSoukhtedel.util.ImageUtils;
import ir.sharif.math.zahraSoukhtedel.view.ViewManager;
import ir.sharif.math.zahraSoukhtedel.view.messagingPage.MessageViewerFXController;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.paint.ImagePattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessageViewerController {

    static private final Logger logger = LogManager.getLogger(MessageViewerController.class);

    public void refresh(boolean deactivated, String messageImage, String messageContent,
                        LocalDateTime messageDateTime, String messageSender) {
        if(!(ViewManager.getInstance().getCurPage().getFxController() instanceof MessageViewerFXController))
            return;
        MessageViewerFXController messageViewerFXController = (MessageViewerFXController)
                ViewManager.getInstance().getCurPage().getFxController();

        ImageUtils imageUtils = new ImageUtils();
        BufferedImage image = null;
        try {
            image = imageUtils.toBufferedImage(messageImage);
        } catch (IOException e) {
            logger.warn("can't convert byte array to buffered image");
            e.printStackTrace();
        }
        if (image != null)
            messageViewerFXController.getPhotoBox().setFill(new ImagePattern(SwingFXUtils.toFXImage(image, null)));

        Platform.runLater(() -> {
            if (deactivated)
                messageViewerFXController.deactivateButtons();
            messageViewerFXController.setMessageContentField(messageContent);
            messageViewerFXController.setMessageDateLabel(messageDateTime.
                    format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            messageViewerFXController.setSenderLabel(messageSender);
        });
    }
}
