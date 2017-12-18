package main.rmi;

import main.play.Playlist;
import main.play.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ClientContainer extends UnicastRemoteObject implements IPlaylistContainer, INotifier, IAccountManager {
    User user;

    protected ClientContainer() throws RemoteException {

    }

    @Override
    public boolean register(User user) {
        return false;
    }

    @Override
    public boolean login(User user) {
        return false;
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
