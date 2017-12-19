package main.database.data;

import main.play.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface IUserContext {
    User login(String name, String password) throws SQLException;
    User getById(UUID id) throws SQLException;
    List<User> getAll() throws SQLException;
    boolean insert(User user) throws SQLException;
    boolean delete(UUID id) throws SQLException;
    User getFromResultSet(ResultSet rs) throws SQLException;
}
