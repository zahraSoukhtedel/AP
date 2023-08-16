package ir.sharif.math.zahraSoukhtedel.view;

import javafx.fxml.FXML;

public abstract class FXController {

    @FXML
    public void back() { ViewManager.getInstance().back(); }

    @FXML
    public void goToMainPage() { ViewManager.getInstance().goToMainPage(); }

    @FXML
    public void clear() {}

}
