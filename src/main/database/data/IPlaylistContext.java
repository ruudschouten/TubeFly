package main.database.data;

import main.play.Playlist;
import main.play.User;

import java.util.List;
import java.util.UUID;

public interface IPlaylistContext {
    Playlist getById(UUID id);
    List<Playlist> getAll();
    List<Playlist> getFromCreator(User user);
    void insert(Playlist playlist);
    void delete(UUID id);
}
