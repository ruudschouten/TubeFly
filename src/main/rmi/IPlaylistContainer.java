package main.rmi;

import main.play.Playlist;

import java.rmi.Remote;
import java.util.List;

public interface IPlaylistContainer extends Remote {
    public List<Playlist> getPlaylists();
    public Playlist getPlaylist(int id);
    public boolean uploadPlaylist(Playlist playlist);
    public boolean updatePlaylist(Playlist playlist);
}
