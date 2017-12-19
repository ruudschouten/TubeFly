package rmi;

import fontyspublisher.IRemotePropertyListener;
import log.Logger;
import play.Playlist;
import play.User;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ClientContainer extends UnicastRemoteObject implements IRemotePropertyListener, IPlaylistContainer, INotifier, IAccountManager {
    private Logger logger;
    private IRemotePropertyListener publisher;
    private ServerContainer server;
    private User user;

    public ClientContainer(IRemotePropertyListener publisher, ServerContainer server) throws RemoteException {
        this.publisher = publisher;
        this.server = server;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {

    }

    @Override
    public boolean register(User user) {
        return server.register(user);
    }

    @Override
    public boolean login(User user) {
        return server.login(user);
    }

    @Override
    public boolean logout(User user) {
        return false;
    }

    @Override
    public boolean subscribeToArtist(User user, String artist) {
        return false;
    }

    @Override
    public boolean notifyUser() {
        return false;
    }

    @Override
    public List<Playlist> getPlaylists() {
        return new ArrayList<>();
    }

    @Override
    public Playlist getPlaylist(int id) {
        return null;
    }

    @Override
    public boolean uploadPlaylist(Playlist playlist) {
        return false;
    }

    @Override
    public boolean updatePlaylist(Playlist playlist) {
        return false;
    }
}
