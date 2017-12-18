package main.database.logic;

import main.database.data.IUserContext;
import main.play.User;

import java.util.List;
import java.util.UUID;

public class UserContext implements IUserContext {
    @Override
    public User getById(UUID id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public void insert(User user) {

    }

    @Override
    public void delete(UUID id) {

    }
}
