package rmi;

import play.User;

import java.rmi.Remote;
import java.sql.SQLException;

public interface IAccountManager extends Remote {
    public boolean register(User user);
    public User login(String mail, String password) throws SQLException;
    public boolean logout(User user);
}
