package ui.client;

import fontyspublisher.IRemotePublisherForListener;
import fontyspublisher.SharedData;
import log.Logger;
import rmi.ClientContainer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;

public class RMIClient {
    private int port;
    private String registryName;
    private String serverName;
    private String publisherName;

    private Logger logger;

    private void initSharedData() {
        //Zodat deze bij server kan
        port = SharedData.getPort();
        registryName = SharedData.getRegistryName();
        serverName = SharedData.getServerName();
        publisherName = SharedData.getPublisherName();
    }

    public void setupManager() {
        logger = new Logger("RmiClient", Level.ALL, Level.SEVERE);
        initSharedData();
        ClientContainer container;

        //Maakt ClientManager aan
        try {
            Registry registry = LocateRegistry.getRegistry(registryName, port);
            //TODO: Set all interfaces
            IRemotePublisherForListener publisher = (IRemotePublisherForListener) registry.lookup(publisherName);
//            IPartyManager server = (IPartyManager) registry.lookup(serverName);
//            container = new ClientContainer(publisher, server);

        } catch (RemoteException | NotBoundException e) {
            logger.log(Level.INFO, e.getMessage());
        }
    }
}
