package ir.sharif.math.zahraSoukhtedel.request.settingsPage;

import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.request.RequestVisitor;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import lombok.ToString;

@ToString
public class EditPrivacySettingsRequest extends Request {
    private final boolean isPrivate;
    private final String lastSeenType;
    private final String password;

    public EditPrivacySettingsRequest(boolean isPrivate, String lastSeenType, String password) {
        this.isPrivate = isPrivate;
        this.lastSeenType = lastSeenType;
        this.password = password;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.editPrivacySettings(isPrivate, lastSeenType, password);
    }
}
