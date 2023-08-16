package ir.sharif.math.zahraSoukhtedel.request.messagingPage.messageSendingPage;

import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.request.RequestVisitor;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import lombok.Getter;

import java.time.LocalDateTime;

public class NewMessageRequest extends Request {

    @Getter
    private final String avatarString;
    @Getter
    private final String messageContent;
    @Getter
    private final Integer receiverId;
    @Getter
    private final LocalDateTime dateTime;
    @Getter
    private final boolean isTiming;

    public NewMessageRequest(String avatarString, String messageContent, Integer receiverId, LocalDateTime dateTime, boolean isTiming) {
        this.avatarString = avatarString;
        this.messageContent = messageContent;
        this.receiverId = receiverId;
        //############
        this.dateTime = dateTime;
        this.isTiming = isTiming;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.newMessage(avatarString, messageContent, receiverId, dateTime, isTiming);
    }

    @Override
    public String toString() {
        return "NewMessageRequest{" +
                "messageContent='" + messageContent + '\'' +
                ", receiverId=" + receiverId +
                '}';
    }
}
