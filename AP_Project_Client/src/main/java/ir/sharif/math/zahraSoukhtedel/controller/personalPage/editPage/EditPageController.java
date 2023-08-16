package ir.sharif.math.zahraSoukhtedel.controller.personalPage.editPage;

import ir.sharif.math.zahraSoukhtedel.view.Page;
import ir.sharif.math.zahraSoukhtedel.view.ViewManager;
import ir.sharif.math.zahraSoukhtedel.view.personalPage.editPage.EditFXController;
import javafx.application.Platform;

import java.time.LocalDate;

public class EditPageController {

    public void switchToEditPage(String username, String firstname, String lastname, String bio, LocalDate birthdate, String email,
                        String phoneNumber, String lastSeenType, boolean isPrivate, boolean isPublicData) {
        Platform.runLater(() -> {
            ViewManager.getInstance().setPage(new Page("editPage"));
            EditFXController editFXController = (EditFXController) ViewManager.getInstance().getCurPage().getFxController();
            editFXController.setUsernameField(username);
            editFXController.setFirstnameField(firstname);
            editFXController.setLastnameField(lastname);
            editFXController.setBioField(bio);
            editFXController.setBirthdateField(birthdate);
            editFXController.setEmailField(email);
            editFXController.setPhoneNumberField(phoneNumber);
            editFXController.setLastSeenField(lastSeenType);
            if(isPrivate)
                editFXController.setAccountPrivacyField("private");
            else
                editFXController.setAccountPrivacyField("public");
            if(isPublicData)
                editFXController.setDataPrivacyField("public");
            else
                editFXController.setDataPrivacyField("private");
        });
    }

    public void applyEditResponse(String error) {
        if (!(ViewManager.getInstance().getCurPage().getFxController() instanceof EditFXController))
            return;
        EditFXController editFXController = (EditFXController) ViewManager.getInstance().getCurPage().getFxController();
        Platform.runLater(() -> {
            if(error.length() == 0)
                ViewManager.getInstance().back();
            else
                editFXController.setError(error);
        });
    }
}
