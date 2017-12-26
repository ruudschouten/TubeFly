package database.logic;

import database.data.IUserContext;
import play.Gender;
import play.User;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MockUserContext implements IUserContext {
    private ArrayList<User> users = new ArrayList<>();
    private boolean initialized = false;

    private void initialize() {
        users.add(new User("Blappole", "real@mail.com", "pass", "Address", Gender.MALE));
        users.add(new User("yes", "yes", "yes", "yes", Gender.MALE));
        initialized = true;
    }

    @Override
    public User login(String mail, String password) throws SQLException {
        if(!initialized) initialize();
        for (User u : users) {
            if(Objects.equals(u.getMail(), mail) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public User getById(UUID id) throws SQLException {
        if(!initialized) initialize();
        for (User u : users) {
            if(Objects.equals(u.getId(), id)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public List<User> getAll() throws SQLException {
        if(!initialized) initialize();
        return users;
    }

    @Override
    public boolean insert(User user) throws SQLException {
        if(!initialized) initialize();
        return users.add(user);
    }

    @Override
    public boolean delete(UUID id) throws SQLException {
        if(!initialized) initialize();
        return users.removeIf(u -> u.getId().equals(id));
    }

    @Override
    public User getFromResultSet(ResultSet rs) throws SQLException {
        //This isn't needed for mock data
        throw new NotImplementedException();
    }
}
