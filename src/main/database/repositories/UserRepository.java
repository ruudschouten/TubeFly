package main.database.repositories;

import main.database.data.IUserContext;
import main.log.Logger;
import main.play.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class UserRepository implements IUserContext {
    private Logger logger;
    private IUserContext context;

    public UserRepository(IUserContext context) {
        this.context = context;
        logger = new Logger(context.getClass().getName(), Level.ALL, Level.ALL);
    }

    @Override
    public User login(String name, String password) throws SQLException {
        return context.login(name, password);
    }

    @Override
    public User getById(UUID id) {
        try {
            return context.getById(id);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.toString());
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        try {
            return context.getAll();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.toString());
        }
        return new ArrayList<>();
    }

    @Override
    public boolean insert(User user) {
        try {
            return context.insert(user);
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
    public User getFromResultSet(ResultSet rs) throws SQLException {
        return context.getFromResultSet(rs);
    }
}
