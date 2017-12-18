package main.database.repositories;

import main.database.data.IPlaylistContext;
import main.database.logic.PlaylistContext;
import main.play.Playlist;
import main.play.User;

import java.util.List;
import java.util.UUID;

public class PlaylistRepository implements IPlaylistContext {
    PlaylistContext context = new PlaylistContext();

    @Override
    public Playlist getById(UUID id) {
        return context.getById(id);
    }

    @Override
    public List<Playlist> getAll() {
        return context.getAll();
    }

    @Override
    public List<Playlist> getFromCreator(User user) {
        return context.getFromCreator(user);
    }

    @Override
    public void insert(Playlist playlist) {
        context.insert(playlist);
    }

    @Override
    public void delete(UUID id) {
        context.delete(id);
    }
}
