package main.rmi;

import main.play.User;

import java.rmi.Remote;

public interface IAccountManager extends Remote {
    public boolean register(User user);
    public boolean login(User user);
    public boolean logout(User user);
}
