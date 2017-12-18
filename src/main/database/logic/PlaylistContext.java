package main.database.logic;

import main.database.data.IPlaylistContext;
import main.play.Playlist;
import main.play.User;

import java.util.List;
import java.util.UUID;

public class PlaylistContext implements IPlaylistContext {
    @Override
    public Playlist getById(UUID id) {
        return null;
    }

    @Override
    public List<Playlist> getAll() {
        return null;
    }

    @Override
    public List<Playlist> getFromCreator(User user) {
        return null;
    }

    @Override
    public void insert(Playlist playlist) {

    }

    @Override
    public void delete(UUID id) {

    }
}
