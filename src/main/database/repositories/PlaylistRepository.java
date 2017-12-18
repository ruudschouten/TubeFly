package main.database.repositories;

import main.database.data.IPlaylistContext;
import main.database.logic.PlaylistContext;
import main.log.Logger;
import main.play.Playlist;
import main.play.User;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            logger.log(Level.SEVERE, e.toString());
        }
        return null;
    }

    @Override
    public List<Playlist> getAll() {
        try {
            return context.getAll();
        } catch (SQLException | RemoteException e) {
            logger.log(Level.SEVERE, e.toString());
        }
        return null;
    }

    @Override
    public List<Playlist> getFromCreator(User user) {
        try {
            return context.getFromCreator(user);
        } catch (SQLException | RemoteException e) {
            logger.log(Level.SEVERE, e.toString());
        }
        return null;
    }

    @Override
    public boolean insert(Playlist playlist) {
        try {
            return context.insert(playlist);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.toString());
        }
        return false;
    }

    @Override
    public boolean delete(UUID id) {
        try {
            return context.delete(id);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.toString());
        }
        return false;
    }

    @Override
    public Playlist getFromResultSet(ResultSet rs) throws SQLException, RemoteException {
        return context.getFromResultSet(rs);
    }
}
