package main.database.repositories;

import main.database.data.ISongContext;
import main.database.logic.SongContext;
import main.play.Song;

import java.util.List;

public class SongRepository implements ISongContext {
    SongContext context = new SongContext();

    @Override
    public Song getById() {
        return context.getById();
    }

    @Override
    public List<Song> getAll() {
        return context.getAll();
    }

    @Override
    public List<Song> getByArtist(String artist) {
        return context.getByArtist(artist);
    }

    @Override
    public void insert(Song song) {
        context.insert(song);
    }

    @Override
    public void delete(Song song) {
        context.delete(song);
    }
}
