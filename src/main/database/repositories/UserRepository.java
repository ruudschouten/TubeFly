package main.database.repositories;

import main.database.data.IUserContext;
import main.database.logic.UserContext;
import main.play.User;

import java.util.List;
import java.util.UUID;

public class UserRepository implements IUserContext {
    UserContext context = new UserContext();
    @Override
    public User getById(UUID id) {
        return context.getById(id);
    }

    @Override
    public List<User> getAll() {
        return context.getAll();
    }

    @Override
    public void insert(User user) {
        context.insert(user);
    }

    @Override
    public void delete(UUID id) {
        context.delete(id);
    }
}
