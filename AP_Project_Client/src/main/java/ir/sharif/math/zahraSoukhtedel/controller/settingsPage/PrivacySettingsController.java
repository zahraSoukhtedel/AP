package ir.sharif.math.zahraSoukhtedel.controller.settingsPage;

import ir.sharif.math.zahraSoukhtedel.view.Page;
import ir.sharif.math.zahraSoukhtedel.view.ViewManager;
import ir.sharif.math.zahraSoukhtedel.view.settings.PrivacySettingsFXController;
import javafx.application.Platform;

public class PrivacySettingsController {
    public void switchToPrivacySettingsPage(boolean isPrivate, String lastSeenType, String password) {
        Platform.runLater(() -> {
            ViewManager.getInstance().setPage(new Page("privacySettingsPage"));
            PrivacySettingsFXController privacySettingsFXController = (PrivacySettingsFXController)
                    ViewManager.getInstance().getCurPage().getFxController();
            privacySettingsFXController.setPrivacy(isPrivate);
            privacySettingsFXController.setLastSeen(lastSeenType);
            privacySettingsFXController.setPasswordField(password);
        });
    }
}
