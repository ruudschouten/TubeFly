package database.repositories;

import database.data.IArtistContext;
import log.Logger;
import play.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class ArtistRepository implements IArtistContext {
    private Logger logger;
    private IArtistContext context;

    public ArtistRepository(IArtistContext context) {
        this.context = context;
        logger = new Logger(context.getClass().getName(), Level.ALL, Level.ALL);
    }

    @Override
    public String getFromId(int id) throws SQLException {
        return context.getFromId(id);
    }

    @Override
    public int getFromName(String name) throws SQLException {
        return context.getFromName(name);
    }

    @Override
    public List<User> getSubscribersAll(String artist) {
        try {
            return context.getSubscribersAll(artist);
        } catch (SQLException e) {
            logger.logE(Level.SEVERE, e);
        }
        return new ArrayList<>();
    }

    @Override
    public boolean insert(String artistName) {
        try {
            return context.insert(artistName);
        } catch (SQLException e) {
            logger.logE(Level.SEVERE, e);
        }
        return false;
    }

    @Override
    public boolean delete(String artistName) {
        try {
            return context.delete(artistName);
        } catch (SQLException e) {
            logger.logE(Level.SEVERE, e);
        }
        return false;
    }
}

