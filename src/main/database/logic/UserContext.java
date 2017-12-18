package main.database.logic;

import main.database.data.Database;
import main.database.data.IUserContext;
import main.play.Gender;
import main.play.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserContext implements IUserContext {
    @Override
    public User getById(UUID id) throws SQLException {
        User user = null;
        Connection con = Database.getCon();
        PreparedStatement statement = con.prepareStatement("SELECT * FROM User u WHERE u.ID = ?");
        statement.setString(1, id.toString());
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            user = getFromResultSet(rs);
        }
        return user;
    }

    @Override
    public List<User> getAll() throws SQLException {
        List<User> users = new ArrayList<>();
        Connection con = Database.getCon();
        PreparedStatement statement = con.prepareStatement("SELECT * FROM User");
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            users.add(getFromResultSet(rs));
        }
        return users;
    }

    @Override
    public boolean insert(User user) throws SQLException {
        boolean success;
        Connection con = Database.getCon();
        PreparedStatement statement = con.prepareStatement("INSERT INTO user (ID, Name, Password, Address, Gender) values (?, ?, ? ,?, ?)");
        statement.setString(1, user.getId().toString());
        statement.setString(2, user.getName());
        statement.setString(3, user.getPassword()); //TODO: Hash this
        statement.setString(4, user.getAddress());
        statement.setString(5, user.getGender().toString());
        success = statement.execute();
        return success;
    }

    @Override
    public boolean delete(UUID id) throws SQLException {
        boolean success;
        Connection con = Database.getCon();
        PreparedStatement statement = con.prepareStatement("DELETE FROM user WHERE ID = ?");
        statement.setString(1, id.toString());
        success = statement.execute();
        return success;
    }

    @Override
    public User getFromResultSet(ResultSet rs) throws SQLException {
        String id = rs.getString("ID");
        String name = rs.getString("Name");
        String password = rs.getString("Password");
        String address = rs.getString("Address");
        Gender gender = Gender.valueOf(rs.getString("Gender").toUpperCase());
        return new User(id, name, password, address, gender);
    }
}
