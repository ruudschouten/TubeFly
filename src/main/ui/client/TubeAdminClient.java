package ui.client;

import fontyspublisher.IRemotePublisherForListener;
import javafx.application.Application;
import javafx.stage.Stage;
import log.Logger;
import rmi.ClientContainer;
import ui.UITools;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;

public class TubeAdminClient extends Application {
    ClientContainer container;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.close();
//        container = new ClientContainer();
        UITools.UIManager uiManager = new UITools.UIManager();
        uiManager.setContainer(container);
        uiManager.loadFXML("menu.fxml", "Menu");
    }
}
