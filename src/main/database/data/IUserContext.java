package main.database.data;

import main.play.User;

import java.util.List;
import java.util.UUID;

public interface IUserContext {
    User getById(UUID id);
    List<User> getAll();
    void insert(User user);
    void delete(UUID id);
}
