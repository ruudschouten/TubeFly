package database.logic;

import database.data.Database;
import database.data.IPlaylistContext;
import database.repositories.SongRepository;
import database.repositories.UserRepository;
import play.Playlist;
import play.Song;
import play.User;

import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DatabasePlaylistContext implements IPlaylistContext {
    @Override
    public Playlist getById(UUID id) throws SQLException, RemoteException {
        Playlist playlist = null;
        PreparedStatement statement = null;
        try {
            statement = Database.getCon().prepareStatement("SELECT * FROM playlist p WHERE p.ID = ?");
            statement.setString(1, id.toString());
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    playlist = getFromResultSet(rs);
                }
            }
        } finally {
            if (statement != null) statement.close();
        }
        return playlist;
    }

    @Override
    public List<Playlist> getAll() throws SQLException, RemoteException {
        ArrayList<Playlist> playlists = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = Database.getCon().prepareStatement("SELECT * FROM playlist p");
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    playlists.add(getFromResultSet(rs));
                }
            }
        } finally {
            if (statement != null) statement.close();
        }
        return playlists;
    }

    @Override
    public List<Playlist> getFromCreator(User user) throws SQLException, RemoteException {
        ArrayList<Playlist> playlists = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = Database.getCon().prepareStatement("SELECT * FROM playlist p WHERE CreatorID = ?");
            statement.setString(1, user.getId().toString());
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    playlists.add(getFromResultSet(rs));
                }
            }
        } finally {
            if (statement != null) statement.close();
        }
        return playlists;
    }

    @Override
    public List<User> getFollowers(UUID id) throws SQLException, RemoteException {
        ArrayList<User> followers = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = Database.getCon().prepareStatement("SELECT * FROM playlist p " +
                    "INNER JOIN playlist_follower pf ON p.ID = pf.PlaylistID " +
                    "INNER JOIN user u ON pf.UserID = u.ID " +
                    "WHERE p.ID = ?");
            statement.setString(1, id.toString());
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    followers.add(new UserRepository(new DatabaseUserContext()).getFromResultSet(rs));
                }
            }
        } finally {
            if (statement != null) statement.close();
        }
        return followers;
    }

    @Override
    public boolean addSong(Song song, UUID id) throws SQLException, RemoteException {
        if (new SongRepository(new DatabaseSongContext()).insert(song)) {
            boolean success;
            PreparedStatement statement = null;
            try {
                statement = Database.getCon().prepareStatement("INSERT INTO playlist_song(SongURL, PlaylistID) VALUES(?, ?)");
                statement.setString(1, song.getURL());
                statement.setString(2, id.toString());
                success = statement.execute();
            } finally {
                if (statement != null) statement.close();
            }
            return success;
        }
        return false;
    }

    @Override
    public boolean removeSong(Song song, UUID id) throws SQLException, RemoteException {
        boolean success;
        PreparedStatement statement = null;
        try {
            statement = Database.getCon().prepareStatement("DELETE FROM playlist_song WHERE SongURL = ? AND PlaylistID = ?");
            statement.setString(1, song.getURL());
            statement.setString(2, id.toString());
            success = statement.execute();
        } finally {
            if (statement != null) statement.close();
        }
        return success;
    }

    @Override
    public boolean insert(Playlist playlist) throws SQLException {
        boolean success;
        PreparedStatement statement = null;
        try {
            statement = Database.getCon().prepareStatement("INSERT INTO playlist (ID, CreatorID, Name, Description) VALUES (?, ?, ? ,?)");
            statement.setString(1, playlist.getId().toString());
            statement.setString(2, playlist.getCreator().getId().toString());
            statement.setString(3, playlist.getName());
            statement.setString(4, playlist.getDescription());
            success = statement.execute();
        } finally {
            if (statement != null) statement.close();
        }
        return success;
    }

    @Override
    public boolean update(UUID id, String name, String description) throws SQLException, RemoteException {
        boolean success;
        PreparedStatement statement = null;
        try {
            statement = Database.getCon().prepareStatement("UPDATE playlist SET Name = ?, Description = ? WHERE ID = ?");
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setString(3, id.toString());
            success = statement.execute();
        } finally {
            if (statement != null) statement.close();
        }
        return success;
    }

    @Override
    public boolean follow(Playlist playlist, User user) throws SQLException, RemoteException {
        boolean success;
        PreparedStatement statement = null;
        try {
            statement = Database.getCon().prepareStatement("INSERT INTO playlist_follower (PlaylistID,  UserID) VALUES (?, ?)");
            statement.setString(1, playlist.getId().toString());
            statement.setString(2, user.getId().toString());
            success = statement.execute();
        } finally {
            if (statement != null) statement.close();
        }
        return success;
    }

    @Override
    public boolean unfollow(Playlist playlist, User user) throws SQLException, RemoteException {boolean success;
        PreparedStatement statement = null;
        try {
            statement = Database.getCon().prepareStatement("DELETE FROM playlist_follower WHERE PlaylistID = ? AND USerID = ?");
            statement.setString(1, playlist.getId().toString());
            statement.setString(2, user.getId().toString());
            success = statement.execute();
        } finally {
            if (statement != null) statement.close();
        }
        return success;
    }

    @Override
    public boolean delete(UUID id) throws SQLException {
        boolean success;
        PreparedStatement statement = null;
        try {
            statement = Database.getCon().prepareStatement("DELETE FROM playlist WHERE ID = ?");
            statement.setString(1, id.toString());
            success = statement.execute();
        } finally {
            if (statement != null) statement.close();
        }
        return success;
    }

    @Override
    public Playlist getFromResultSet(ResultSet rs) throws SQLException, RemoteException {
        String id = rs.getString("ID");
        String name = rs.getString("Name");
        String description = rs.getString("Description");
        User creator = new UserRepository(new DatabaseUserContext()).getById(UUID.fromString(rs.getString("CreatorID")));
        ArrayList<Song> songs = (ArrayList<Song>) new SongRepository(new DatabaseSongContext()).getAllFromPlaylist(UUID.fromString(id));
        ArrayList<User> followers = (ArrayList<User>) getFollowers(UUID.fromString(id));
        return new Playlist(id, name, description, creator, songs, followers);
    }
}
