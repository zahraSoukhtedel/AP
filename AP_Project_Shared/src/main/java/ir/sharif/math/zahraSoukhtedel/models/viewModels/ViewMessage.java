package ir.sharif.math.zahraSoukhtedel.models.viewModels;

import lombok.Getter;

import java.time.LocalDateTime;

public class ViewMessage {
    @Getter
    private final Integer messageId;
    @Getter
    private final String messageContent;
    @Getter
    private final LocalDateTime messageDateTime;
    @Getter
    private final String messageState;
    @Getter
    private final String messageSender;

    public ViewMessage(Integer messageId, String messageContent, LocalDateTime messageDateTime,
                       String messageState, String messageSender) {
        this.messageId = messageId;
        this.messageContent = messageContent;
        this.messageDateTime = messageDateTime;
        this.messageState = messageState;
        this.messageSender = messageSender;
    }

}
