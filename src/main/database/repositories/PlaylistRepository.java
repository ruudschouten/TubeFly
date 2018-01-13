package database.repositories;

import database.data.IPlaylistContext;
import log.Logger;
import play.Playlist;
import play.Song;
import play.User;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class PlaylistRepository implements IPlaylistContext {
    private Logger logger;
    private IPlaylistContext context;

    public PlaylistRepository(IPlaylistContext context) {
        this.context = context;
        logger = new Logger(context.getClass().getName(), Level.ALL, Level.ALL);
    }

    @Override
    public Playlist getById(UUID id) {
        try {
            return context.getById(id);
        } catch (SQLException | RemoteException e) {
            logger.logE(Level.SEVERE, e);
        }
        return null;
    }

    @Override
    public List<Playlist> getAll() {
        try {
            return context.getAll();
        } catch (SQLException | RemoteException e) {
            logger.logE(Level.SEVERE, e);
        }
        return new ArrayList<>();
    }

    @Override
    public List<Playlist> getAll(String searchCriteria) throws SQLException, RemoteException {

        try {
            return context.getAll(searchCriteria);
        } catch (SQLException | RemoteException e) {
            logger.logE(Level.SEVERE, e);
        }
        return new ArrayList<>();
    }

    @Override
    public List<Playlist> getFromCreator(User user) {
        try {
            return context.getFromCreator(user);
        } catch (SQLException | RemoteException e) {
            logger.logE(Level.SEVERE, e);
        }
        return new ArrayList<>();
    }

    @Override
    public List<Playlist> getFromFollower(User user) throws SQLException, RemoteException {
        try {
            return context.getFromFollower(user);
        } catch (SQLException | RemoteException e) {
            logger.logE(Level.SEVERE, e);
        }
        return new ArrayList<>();
    }

    @Override
    public List<User> getFollowers(UUID id) throws SQLException, RemoteException {
        try {
            return context.getFollowers(id);
        } catch (SQLException | RemoteException e) {
            logger.logE(Level.SEVERE, e);
        }
        return new ArrayList<>();
    }

    @Override
    public boolean addSong(Song song, UUID id) throws SQLException, RemoteException {
        try {
            return context.addSong(song, id);
        } catch (SQLException | RemoteException e) {
            logger.logE(Level.SEVERE, e);
        }
        return false;
    }

    @Override
    public boolean removeSong(Song song, UUID id) throws SQLException, RemoteException {
        try {
            return context.removeSong(song, id);
        } catch (SQLException | RemoteException e) {
            logger.logE(Level.SEVERE, e);
        }
        return false;
    }

    @Override
    public boolean insert(Playlist playlist) {
        try {
            return context.insert(playlist);
        } catch (SQLException | RemoteException e) {
            logger.logE(Level.SEVERE, e);
        }
        return false;
    }

    @Override
    public boolean update(UUID id, String name, String description) throws SQLException, RemoteException {
        try {
            return context.update(id, name, description);
        } catch (SQLException | RemoteException e) {
            logger.logE(Level.SEVERE, e);
        }
        return false;
    }

    @Override
    public boolean follow(Playlist playlist, User user) throws SQLException, RemoteException {
        try {
            return context.follow(playlist, user);
        } catch (SQLException | RemoteException e) {
            logger.logE(Level.SEVERE, e);
        }
        return false;
    }

    @Override
    public boolean unfollow(Playlist playlist, User user) throws SQLException, RemoteException {
        try {
            return context.unfollow(playlist, user);
        } catch (SQLException | RemoteException e) {
            logger.logE(Level.SEVERE, e);
        }
        return false;
    }

    @Override
    public boolean delete(UUID id) {
        try {
            return context.delete(id);
        } catch (SQLException | RemoteException e) {
            logger.logE(Level.SEVERE, e);
        }
        return false;
    }

    @Override
    public Playlist getFromResultSet(ResultSet rs) throws SQLException, RemoteException {
        try {
            return context.getFromResultSet(rs);
        } catch (SQLException e) {
            logger.logE(Level.SEVERE, e);
        }
        return null;
    }
}
