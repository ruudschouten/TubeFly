package rmi;

import play.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

public interface IAccountManager extends Remote {
    public boolean register(User user) throws RemoteException;
    public User login(String mail, String password) throws SQLException, RemoteException;
    public boolean logout(User user) throws RemoteException;
}
