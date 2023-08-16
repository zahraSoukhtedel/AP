package ir.sharif.math.zahraSoukhtedel.response.personalPage.notificationsPage;

import ir.sharif.math.zahraSoukhtedel.models.viewModels.ViewUser;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.response.ResponseVisitor;

import java.util.List;

public class UpdateNotificationsPageResponse extends Response {
    private final List<String> requestMessages;
    private final List<String> systemMessages;
    private final List<ViewUser> requests;

    public UpdateNotificationsPageResponse(List<String> requestMessages, List<String> systemMessages, List<ViewUser> requests) {
        this.requestMessages = requestMessages;
        this.systemMessages = systemMessages;
        this.requests = requests;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.updateNotificationsPage(requestMessages, systemMessages, requests);
    }
}
