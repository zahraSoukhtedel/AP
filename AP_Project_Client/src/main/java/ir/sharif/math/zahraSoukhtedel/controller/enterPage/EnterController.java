package ir.sharif.math.zahraSoukhtedel.controller.enterPage;
import ir.sharif.math.zahraSoukhtedel.view.ViewManager;
import ir.sharif.math.zahraSoukhtedel.view.enterPage.SignInFXController;
import ir.sharif.math.zahraSoukhtedel.view.enterPage.SignUpFXController;
import javafx.application.Platform;

public class EnterController {

    public void enter(boolean success, String message) {
        if(success) {
            Platform.runLater(() -> ViewManager.getInstance().goToMainPage());
            return;
        }
        if(ViewManager.getInstance().getCurPage().getFxController() instanceof SignInFXController)
            Platform.runLater(() ->
                    ((SignInFXController)ViewManager.getInstance().getCurPage().getFxController()).setError(message));
        else
            Platform.runLater(() ->
                    ((SignUpFXController)ViewManager.getInstance().getCurPage().getFxController()).setError(message));
    }
}
