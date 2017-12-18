package main.database.repositories;

import main.database.data.IArtistContext;
import main.database.data.IUserContext;
import main.database.logic.ArtistContext;
import main.log.Logger;
import main.play.User;

import java.sql.SQLException;
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
            logger.log(Level.SEVERE, e.toString());
        }
        return null;
    }

    @Override
    public boolean insert(String artistName) {
        try {
            return context.insert(artistName);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.toString());
        }
        return false;
    }

    @Override
    public boolean delete(String artistName) {
        try {
            return context.delete(artistName);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.toString());
        }
        return false;
    }
}

