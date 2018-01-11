package rmi;

import play.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface INotifier extends Remote {
    public boolean notifyUser() throws RemoteException;
}
