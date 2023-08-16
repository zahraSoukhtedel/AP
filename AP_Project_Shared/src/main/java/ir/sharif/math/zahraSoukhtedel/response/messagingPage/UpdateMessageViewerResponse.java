package ir.sharif.math.zahraSoukhtedel.response.messagingPage;

import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.response.ResponseVisitor;

import java.time.LocalDateTime;

public class UpdateMessageViewerResponse extends Response {
    private final boolean deactivated;
    private final String messageImage;
    private final String messageContent;
    private final LocalDateTime messageDateTime;
    private final String messageSender;
    private final Integer messageId;

    public UpdateMessageViewerResponse(Integer messageId ,boolean deactivated, String messageImage, String messageContent,
                                       LocalDateTime messageDateTime, String messageSender) {
        this.messageId = messageId;
        this.deactivated = deactivated;
        this.messageImage = messageImage;
        this.messageContent = messageContent;
        this.messageDateTime = messageDateTime;
        this.messageSender = messageSender;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.updateMessageViewerPage(messageId,deactivated, messageImage, messageContent, messageDateTime, messageSender);
    }
}
