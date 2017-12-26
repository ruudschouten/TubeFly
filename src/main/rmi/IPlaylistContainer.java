package rmi;

import play.Playlist;
import play.Song;
import play.User;

import java.rmi.Remote;
import java.util.List;
import java.util.UUID;

public interface IPlaylistContainer extends Remote {
    public List<Playlist> getPlaylists();
    public List<Playlist> getPlaylists(int limit);
    public Playlist getPlaylist(UUID id);
    public Playlist getPlaylist(String searchCriteria);
    public boolean uploadPlaylist(Playlist playlist);
    public boolean updatePlaylist(String name, String description);
    public boolean addSong(Playlist playlist, Song song);
    public boolean removeSong(Playlist playlist, Song song);
    public boolean follow(Playlist playlist, User user);
    public boolean unfollow(Playlist playlist, User user);
}
