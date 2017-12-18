package main.database.repositories;

import main.database.data.ISongContext;
import main.database.logic.SongContext;
import main.log.Logger;
import main.play.Song;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class SongRepository implements ISongContext {
    private Logger logger;
    private ISongContext context;

    public SongRepository(ISongContext context) {
        this.context = context;
        logger = new Logger(context.getClass().getName(), Level.ALL, Level.ALL);
    }

    @Override
    public Song getById(int id) throws SQLException {
        return context.getById(id);
    }

    @Override
    public Song getByName(String name) throws SQLException {
        return context.getByName(name);
    }

    @Override
    public Song getByURL(String url) throws SQLException {
        return context.getByURL(url);
    }

    @Override
    public List<Song> getAll() {
        try {
            return context.getAll();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.toString());
        }
        return null;
    }

    @Override
    public List<Song> getAllFromPlaylist(UUID id) throws SQLException {
        return context.getAllFromPlaylist(id);
    }

    @Override
    public List<Song> getByArtist(String artist) {
        try {
            return context.getByArtist(artist);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.toString());
        }
        return null;
    }

    @Override
    public boolean insert(Song song) {
        try {
            return context.insert(song);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.toString());
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try {
            return context.delete(id);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.toString());
        }
        return false;
    }

    @Override
    public Song getFromResultSet(ResultSet rs) throws SQLException {
        return context.getFromResultSet(rs);
    }
}
