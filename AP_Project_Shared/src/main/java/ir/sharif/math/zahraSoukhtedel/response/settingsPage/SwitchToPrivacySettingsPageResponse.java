package ir.sharif.math.zahraSoukhtedel.response.settingsPage;

import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.response.ResponseVisitor;

public class SwitchToPrivacySettingsPageResponse extends Response {
    private final boolean isPrivate;
    private final String lastSeenType;
    private final String password;

    public SwitchToPrivacySettingsPageResponse(boolean isPrivate, String lastSeenType, String password) {
        this.isPrivate = isPrivate;
        this.lastSeenType = lastSeenType;
        this.password = password;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.switchToSettingsPage(isPrivate, lastSeenType, password);
    }
}
