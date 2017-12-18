package main.database.logic;

import main.database.data.Database;
import main.database.data.ISongContext;
import main.database.repositories.ArtistRepository;
import main.play.Song;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SongContext implements ISongContext {
    @Override
    public Song getById(int id) throws SQLException {
        Song song = null;
        Connection con = Database.getCon();
        PreparedStatement statement = con.prepareStatement("SELECT * FROM Song s WHERE s.ID = ?");
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            song = getFromResultSet(rs);
        }
        return song;
    }

    @Override
    public Song getByName(String name) throws SQLException {
        Song song = null;
        Connection con = Database.getCon();
        PreparedStatement statement = con.prepareStatement("SELECT * FROM Song s WHERE s.Name = ?");
        statement.setString(1, name);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            song = getFromResultSet(rs);
        }
        return song;
    }

    @Override
    public Song getByURL(String url) throws SQLException {
        Song song = null;
        Connection con = Database.getCon();
        PreparedStatement statement = con.prepareStatement("SELECT * FROM Song s WHERE s.URL = ?");
        statement.setString(1, url);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            song = getFromResultSet(rs);
        }
        return song;
    }

    @Override
    public List<Song> getAll() throws SQLException {
        ArrayList<Song> songs = new ArrayList<>();
        Connection con = Database.getCon();
        PreparedStatement statement = con.prepareStatement("SELECT * FROM Song s ");
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            songs.add(getFromResultSet(rs));
        }
        return songs;
    }

    @Override
    public List<Song> getAllFromPlaylist(UUID id) throws SQLException {
        ArrayList<Song> songs = new ArrayList<>();
        Connection con = Database.getCon();
        PreparedStatement statement = con.prepareStatement("SELECT * FROM Song s " +
                "INNER JOIN playlist_song ps on s.ID = ps.SongID" +
                "INNER JOIN playlist p on p.ID = ps.PlaylistID");
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            songs.add(getFromResultSet(rs));
        }
        return songs;
    }

    @Override
    public List<Song> getByArtist(String artist) throws SQLException {
        ArrayList<Song> songs = new ArrayList<>();
        Connection con = Database.getCon();
        PreparedStatement statement = con.prepareStatement("SELECT * FROM Song s WHERE ArtistID = ?");
        statement.setString(1, artist);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            songs.add(getFromResultSet(rs));
        }
        return songs;
    }

    @Override
    public boolean insert(Song song) throws SQLException {
        ArtistRepository artistRepository = new ArtistRepository(new ArtistContext());
        if(artistRepository.getFromName(song.getArtist()) == 0) {
            artistRepository.insert(song.getArtist());
        }

        boolean success;
        Connection con = Database.getCon();
        PreparedStatement statement = con.prepareStatement("INSERT INTO song (ID, ArtistID, URL, Name) values (?, ?, ? ,?)");
        statement.setInt(1, song.getId());
        statement.setInt(2, artistRepository.getFromName(song.getName()));
        statement.setString(3, song.getURL());
        statement.setString(4, song.getName());
        success = statement.execute();
        return success;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        boolean success;
        Connection con = Database.getCon();
        PreparedStatement statement = con.prepareStatement("DELETE FROM song WHERE ID = ?");
        statement.setInt(1, id);
        success = statement.execute();
        return success;
    }

    @Override
    public Song getFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("ID");
        String artist = new ArtistRepository(new ArtistContext()).getFromId(rs.getInt("ArtistID"));
        String url = rs.getString("URL");
        String name = rs.getString("Name");
        return new Song(id,url, name, artist);
    }
}
