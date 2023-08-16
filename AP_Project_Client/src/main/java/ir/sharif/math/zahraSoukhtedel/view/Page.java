package ir.sharif.math.zahraSoukhtedel.view;

import ir.sharif.math.zahraSoukhtedel.Main;
import ir.sharif.math.zahraSoukhtedel.util.Config;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class Page {
    private Scene scene;
    private FXController fxController;

    public Scene getScene() {
        return scene;
    }
    public FXController getFxController() { return fxController; }
    public Page(String fxmlName) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource(
                    Config.getConfig("clientConfig")
                            .getProperty(Config.class, "fxmls")
                            .getProperty(String.class, fxmlName)));

            this.scene = new Scene(fxmlLoader.load());
            this.fxController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
