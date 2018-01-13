package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IContainer extends IPlaylistContainer, IAccountManager, Remote {
    SessionManager.SessionStatus getSessionStatus() throws RemoteException;
}
