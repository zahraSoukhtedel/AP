package ir.sharif.math.zahraSoukhtedel.view.enterPage;

import ir.sharif.math.zahraSoukhtedel.view.FXController;
import ir.sharif.math.zahraSoukhtedel.view.Page;
import ir.sharif.math.zahraSoukhtedel.view.ViewManager;
import javafx.fxml.FXML;

public class EnterFXController extends FXController {
    @FXML
    void signInAction() { ViewManager.getInstance().setPage(new Page("signInPage")); }

    @FXML
    void signUpAction() {
        ViewManager.getInstance().setPage(new Page("signUpPage"));
    }
}