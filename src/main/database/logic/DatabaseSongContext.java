package database.logic;

import database.data.Database;
import database.data.ISongContext;
import database.repositories.ArtistRepository;
import play.Song;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DatabaseSongContext implements ISongContext {
    @Override
    public Song getById(int id) throws SQLException {
        Song song = null;
        PreparedStatement statement = null;
        try {
            statement = Database.getCon().prepareStatement("SELECT * FROM Song s WHERE s.ID = ?");
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    song = getFromResultSet(rs);
                }
            }
        } finally {
            if (statement != null) statement.close();
        }
        return song;
    }

    @Override
    public Song getByName(String name) throws SQLException {
        Song song = null;
        PreparedStatement statement = null;
        try {
            statement = Database.getCon().prepareStatement("SELECT * FROM Song s WHERE s.Name = ?");
            statement.setString(1, name);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    song = getFromResultSet(rs);
                }
            }
        } finally {
            if (statement != null) statement.close();
        }
        return song;
    }

    @Override
    public Song getByURL(String url) throws SQLException {
        Song song = null;
        PreparedStatement statement = null;
        try {
            statement = Database.getCon().prepareStatement("SELECT * FROM Song s WHERE s.URL = ?");
            statement.setString(1, url);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    song = getFromResultSet(rs);
                }
            }
        } finally {
            if (statement != null) statement.close();
        }
        return song;
    }

    @Override
    public List<Song> getAll() throws SQLException {
        ArrayList<Song> songs = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = Database.getCon().prepareStatement("SELECT * FROM Song s ");
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    songs.add(getFromResultSet(rs));
                }
            }
        } finally {
            if (statement != null) statement.close();
        }
        return songs;
    }

    @Override
    public List<Song> getAllFromPlaylist(UUID id) throws SQLException {
        ArrayList<Song> songs = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = Database.getCon().prepareStatement("SELECT * FROM Song s " +
                    "INNER JOIN playlist_song ps ON s.URL = ps.SongURL " +
                    "INNER JOIN playlist p ON p.ID = ps.PlaylistID " +
                    "WHERE p.ID = ?");
            statement.setString(1, id.toString());
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    songs.add(getFromResultSet(rs));
                }
            }
        } finally {
            if (statement != null) statement.close();
        }
        return songs;
    }

    @Override
    public List<Song> getByArtist(String artist) throws SQLException {
        ArrayList<Song> songs = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = Database.getCon().prepareStatement("SELECT * FROM Song WHERE ArtistID = ?");
            statement.setString(1, artist);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    songs.add(getFromResultSet(rs));
                }
            }
        } finally {
            if (statement != null) statement.close();
        }
        return songs;
    }

    @Override
    public boolean insert(Song song) throws SQLException {
        ArtistRepository artistRepository = new ArtistRepository(new DatabaseArtistContext());
        if (artistRepository.getFromName(song.getArtist()) == 0) {
            artistRepository.insert(song.getArtist());
        }

        if (!songExists(song.getURL())) return true;

        PreparedStatement statement = null;
        try {
            statement = Database.getCon().prepareStatement("INSERT INTO song (URL, Title) VALUES (?, ?)");
            statement.setString(1, song.getURL());
            statement.setString(2, song.getName());
            statement.execute();
            addArtistID(song);
            return true;
        } finally {
            if (statement != null) statement.close();
        }
    }

    public boolean songExists(String url) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = Database.getCon().prepareStatement("SELECT EXISTS(SELECT 1 FROM song WHERE URL = ?)");
            statement.setString(1, url);
            return statement.execute();
        } finally {
            if (statement != null) statement.close();
        }
    }

    public boolean addArtistID(Song song) throws SQLException {
        ArtistRepository artistRepository = new ArtistRepository(new DatabaseArtistContext());

        int artistID = artistRepository.getFromName(song.getArtist());
        PreparedStatement statement = null;
        try {
            statement = Database.getCon().prepareStatement("UPDATE song SET ArtistID = ? WHERE URL = ?");
            statement.setInt(1, artistID);
            statement.setString(2, song.getURL());
            statement.execute();
            return true;
        } finally {
            if (statement != null) statement.close();
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = Database.getCon().prepareStatement("DELETE FROM song WHERE ID = ?");
            statement.setInt(1, id);
            statement.execute();
            return true;
        } finally {
            if (statement != null) statement.close();
        }
    }

    @Override
    public Song getFromResultSet(ResultSet rs) throws SQLException {
        String artist = new ArtistRepository(new DatabaseArtistContext()).getFromId(rs.getInt("ArtistID"));
        String url = rs.getString("URL");
        String name = rs.getString("Title");
        return new Song(url, name, artist);
    }
}
