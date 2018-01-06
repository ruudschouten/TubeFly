package rmi;

import play.Playlist;
import play.Song;
import play.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface IPlaylistContainer extends Remote {
    public List<Playlist> getPlaylists() throws RemoteException;

    public List<Playlist> getPlaylists(int limit) throws RemoteException;

    public List<Playlist> getPlaylists(User user) throws RemoteException;

    public List<Playlist> getPlaylists(String searchCriteria) throws RemoteException;

    public Playlist getPlaylist(UUID id) throws RemoteException;

    public boolean uploadPlaylist(Playlist playlist) throws RemoteException;

    public boolean updatePlaylist(Playlist playlist, String name, String description) throws RemoteException, SQLException;

    public boolean addSong(Playlist playlist, Song song) throws RemoteException, SQLException;

    public boolean removeSong(Playlist playlist, Song song) throws RemoteException, SQLException;

    public boolean follow(Playlist playlist, User user) throws RemoteException, SQLException;

    public boolean unfollow(Playlist playlist, User user) throws RemoteException, SQLException;
    public void registerProperty(Playlist playlist) throws RemoteException;
}
