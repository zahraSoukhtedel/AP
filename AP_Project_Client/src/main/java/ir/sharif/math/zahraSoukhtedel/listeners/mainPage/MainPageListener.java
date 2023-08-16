package ir.sharif.math.zahraSoukhtedel.listeners.mainPage;

import ir.sharif.math.zahraSoukhtedel.controller.Client;
import ir.sharif.math.zahraSoukhtedel.controller.GetOnline;
import ir.sharif.math.zahraSoukhtedel.view.Page;
import ir.sharif.math.zahraSoukhtedel.view.ViewManager;
import ir.sharif.math.zahraSoukhtedel.view.mainPage.MainFXController;
import javafx.application.Platform;

import java.net.Socket;

public class MainPageListener {

    public void stringEventOccurred(String event) {
        switch (event) {
            case "myPage":
                if (Client.isOnline())
                    ViewManager.getInstance().setPage(new Page("myPage"));
                else
                    Platform.runLater(() ->
                            ((MainFXController)ViewManager.getInstance().getCurPage().getFxController()).setError("cannot connect to server"));
                break;
            case "settings":
                if (Client.isOnline()){
                    Client.setLoadedSettingPage(true);
                    ViewManager.getInstance().setPage(new Page("settingsPage"));
                }
                else
                if (Client.isLoadedSettingPage()){
                    //TODO
                    ViewManager.getInstance().setPage(new Page("settingsPage"));
                }else {
                    Platform.runLater(() ->
                            ((MainFXController)ViewManager.getInstance().getCurPage().getFxController()).setError("you never load this page"));
                }
                break;
            case "timeline":
                if(Client.isOnline())
                    ViewManager.getInstance().setPage(new Page("timelinePage"));
                else
                    Platform.runLater(() ->
                            ((MainFXController)ViewManager.getInstance().getCurPage().getFxController()).setError("cannot connect to server"));
                break;
            case "explorer":
                if(Client.isOnline())
                    ViewManager.getInstance().setPage(new Page("explorerPage"));
                else
                    Platform.runLater(() ->
                            ((MainFXController)ViewManager.getInstance().getCurPage().getFxController()).setError("cannot connect to server"));
                break;
            case "messaging":
                ViewManager.getInstance().setPage(new Page("messagingPage"));
                break;
            case "online":
                if (Client.isOnline())
                    Platform.runLater(() ->
                            ((MainFXController)ViewManager.getInstance().getCurPage().getFxController()).setError("you're already online"));
                else{
                    Socket socket = GetOnline.checkConnection();
                    if (socket == null){
                        Platform.runLater(() ->
                                ((MainFXController)ViewManager.getInstance().getCurPage().getFxController()).setError("cannot connect to server"));
                    }
                    else {
                        Client.getClient().reStart(socket);
                    }
                }
                break;
            default:
                break;
        }
    }
}
