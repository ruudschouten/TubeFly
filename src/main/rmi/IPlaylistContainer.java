package rmi;

import play.Playlist;
import play.Song;
import play.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

public interface IPlaylistContainer extends Remote {
    public List<Playlist> getPlaylists() throws RemoteException;

    public List<Playlist> getPlaylists(int limit) throws RemoteException;

    public Playlist getPlaylist(UUID id) throws RemoteException;

    public Playlist getPlaylist(String searchCriteria) throws RemoteException;

    public boolean uploadPlaylist(Playlist playlist) throws RemoteException;

    public boolean updatePlaylist(String name, String description) throws RemoteException;

    public boolean addSong(Playlist playlist, Song song) throws RemoteException;

    public boolean removeSong(Playlist playlist, Song song) throws RemoteException;

    public boolean follow(Playlist playlist, User user) throws RemoteException;

    public boolean unfollow(Playlist playlist, User user) throws RemoteException;
}
