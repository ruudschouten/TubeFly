package main.database.data;

import main.play.Song;

import java.util.List;

public interface ISongContext {
    Song getById();
    List<Song> getAll();
    List<Song> getByArtist(String artist);
    void insert(Song song);
    void delete(Song song);
}
