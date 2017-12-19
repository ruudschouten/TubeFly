package rmi;

import play.Playlist;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class AdminClientContainer extends UnicastRemoteObject implements IPlaylistContainer {

    protected AdminClientContainer() throws RemoteException {
    }

    @Override
    public List<Playlist> getPlaylists() {
        return null;
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
