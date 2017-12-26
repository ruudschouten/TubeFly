package rmi;

import play.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface INotifier extends Remote {
    public boolean subscribeToArtist(User user, String artist) throws RemoteException;
    public boolean notifyUser() throws RemoteException;
}
