package rmi;

import play.User;

import java.rmi.Remote;

public interface INotifier extends Remote {
    public boolean subscribeToArtist(User user, String artist);
    public boolean notifyUser();
}
