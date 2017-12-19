package database.data;

import play.Song;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface ISongContext {
    Song getById(int id) throws SQLException;
    Song getByName(String name) throws SQLException;
    Song getByURL(String url) throws SQLException;
    List<Song> getAll() throws SQLException;
    List<Song> getAllFromPlaylist(UUID id) throws SQLException;
    List<Song> getByArtist(String artist) throws SQLException;
    boolean insert(Song song) throws SQLException;
    boolean delete(int id) throws SQLException;
    Song getFromResultSet(ResultSet rs) throws SQLException;
}
