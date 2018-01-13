package ui.server;

import fontyspublisher.RemotePublisher;
import fontyspublisher.SharedData;
import log.Logger;
import rmi.ServerContainer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;

public class TubeServer {
    private static int port;
    private static String serverName;
    private static String publisherName;

    private static Logger logger;
    private static boolean keepRunning = true;
    private static ServerContainer container;

    public static void main(String[] args) {
        logger = new Logger("TubeServer", Level.ALL, Level.ALL);
        initSharedData();
        if(!initRegistry()) return;
        while(keepRunning) {

        }
    }

    private static void initSharedData() {
        port = SharedData.getPort();
        serverName = SharedData.getServerName();
        publisherName = SharedData.getPublisherName();
        System.setProperty("java.rmi.server.hostname", SharedData.getRegistryName());
    }

    private static boolean initRegistry() {
        try {
            logger.log(Level.FINE, "Server will start.");
            RemotePublisher publisher = new RemotePublisher();
            Registry registry = LocateRegistry.createRegistry(port);
            logger.log(Level.INFO, "Registry created");
            registry.rebind(publisherName, publisher);
            logger.log(Level.INFO, "Publisher bound to registry");
            container = new ServerContainer(publisher);
            registry.rebind(serverName, container);
            logger.log(Level.INFO, "Server name bound to registry");
            return true;
        } catch (RemoteException e) {
            logger.logE(Level.SEVERE, e);
            return false;
        }
    }
}
