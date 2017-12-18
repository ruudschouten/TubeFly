package main.database.logic;

import main.database.data.ISongContext;
import main.play.Song;

import java.util.List;

public class SongContext implements ISongContext {
    @Override
    public Song getById() {
        return null;
    }

    @Override
    public List<Song> getAll() {
        return null;
    }

    @Override
    public List<Song> getByArtist(String artist) {
        return null;
    }

    @Override
    public void insert(Song song) {

    }

    @Override
    public void delete(Song song) {

    }
}
