package ir.sharif.math.zahraSoukhtedel.request.messagingPage;

import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.request.RequestVisitor;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import lombok.ToString;


@ToString
public class UpdateMessagingPageRequest extends Request {

    private final Integer chatId;
    private final boolean isChanged;

    public UpdateMessagingPageRequest(Integer chatId, boolean isChanged) {
        this.chatId = chatId;
        this.isChanged = isChanged;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.updateMessagingPage(chatId, isChanged);
    }
}
