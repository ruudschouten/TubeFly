package main.database.logic;

import main.database.data.Database;
import main.database.data.IUserContext;
import main.play.Gender;
import main.play.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserContext implements IUserContext {
    @Override
    public User login(String name, String pass) throws SQLException {
        User user = null;
        PreparedStatement statement = null;
        try {
            statement = Database.getCon().prepareStatement("SELECT * FROM User u WHERE u.Name = ? AND u.Password = ?");
            statement.setString(1, name);
            statement.setString(2, pass);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    user = getFromResultSet(rs);
                }
            }
        } finally {
            if (statement != null) statement.close();
        }
        return user;
    }

    @Override
    public User getById(UUID id) throws SQLException {
        User user = null;
        PreparedStatement statement = null;
        try {
            statement = Database.getCon().prepareStatement("SELECT * FROM User u WHERE u.ID = ?");
            statement.setString(1, id.toString());
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    user = getFromResultSet(rs);
                }
            }
        } finally {
            if (statement != null) statement.close();
        }
        return user;
    }

    @Override
    public List<User> getAll() throws SQLException {
        List<User> users = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = Database.getCon().prepareStatement("SELECT * FROM User");
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    users.add(getFromResultSet(rs));
                }
            }
        } finally {
            if (statement != null) statement.close();
        }
        return users;
    }

    @Override
    public boolean insert(User user) throws SQLException {
        boolean success;
        PreparedStatement statement = null;
        try {
            statement = Database.getCon().prepareStatement("INSERT INTO user (ID, Name, Password, Address, Gender) VALUES (?, ?, ? ,?, ?)");
            statement.setString(1, user.getId().toString());
            statement.setString(2, user.getName());
            statement.setString(3, user.getPassword()); //TODO: Hash this
            statement.setString(4, user.getAddress());
            statement.setString(5, user.getGender().toString());
            success = statement.execute();
        } finally {
            if (statement != null) statement.close();
        }
        return success;
    }

    @Override
    public boolean delete(UUID id) throws SQLException {
        boolean success;
        PreparedStatement statement = null;
        try {
            statement = Database.getCon().prepareStatement("DELETE FROM user WHERE ID = ?");
            statement.setString(1, id.toString());
            success = statement.execute();
        } finally {
            if (statement != null) statement.close();
        }
        return success;
    }

    @Override
    public User getFromResultSet(ResultSet rs) throws SQLException {
        String id = rs.getString("ID");
        String name = rs.getString("Name");
        String mail = rs.getString("Mail");
        String password = rs.getString("Password");
        String address = rs.getString("Address");
        Gender gender = Gender.valueOf(rs.getString("Gender").toUpperCase());
        return new User(id, mail, name, password, address, gender);
    }
}
