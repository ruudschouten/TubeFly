package ui.client;

import fontyspublisher.IRemotePublisherForListener;
import fontyspublisher.SharedData;
import javafx.application.Application;
import javafx.stage.Stage;
import log.Logger;
import rmi.ClientContainer;
import rmi.IContainer;
import ui.ResourceHandler;
import ui.UITools;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;

public class TubeClient extends Application {
    private Logger logger;
    private ClientContainer container;

    //RMI
    private static int port;
    private static String registryName;
    private static String serverName;
    private static String publisherName;

    @Override
    public void start(Stage primaryStage) throws Exception {
        logger = new Logger("TubeClient", Level.ALL, Level.SEVERE);
        initSharedData();
        initPublisher();
        primaryStage.close();

        ResourceHandler.setContainer(container);
        ResourceHandler.setLogger(logger);
        UITools.UIManager uiManager = new UITools.UIManager();
        uiManager.loadFXML("login.fxml", "Login");
    }

    private void initSharedData() {
        port = SharedData.getPort();
        registryName = SharedData.getRegistryName();
        serverName = SharedData.getServerName();
        publisherName = SharedData.getPublisherName();
    }

    private void initPublisher() {
        try {
            Registry registry = LocateRegistry.getRegistry(registryName, port);
            IRemotePublisherForListener publisher = (IRemotePublisherForListener) registry.lookup(publisherName);
            IContainer server = (IContainer) registry.lookup(serverName);
            container = new ClientContainer(publisher, server);
        } catch (RemoteException | NotBoundException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }
}
