package main.database.logic;

import main.database.data.Database;
import main.database.data.IArtistContext;
import main.database.repositories.UserRepository;
import main.play.Gender;
import main.play.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArtistContext implements IArtistContext {
    @Override
    public String getFromId(int id) throws SQLException {
        String name = "";
        Connection con = Database.getCon();
        PreparedStatement statement = con.prepareStatement("SELECT Name FROM artist WHERE ID = ?");
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            name = rs.getString("Name");
        }
        return name;
    }

    @Override
    public int getFromName(String name) throws SQLException {
        int id = 0;
        Connection con = Database.getCon();
        PreparedStatement statement = con.prepareStatement("SELECT Name FROM artist WHERE Name like ?");
        statement.setString(1, name);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            id = rs.getInt("ID");
        }
        return id;
    }

    @Override
    public List<User> getSubscribersAll(String artist) throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        Connection con = Database.getCon();
        PreparedStatement statement = con.prepareStatement("SELECT u.* FROM user_subscribed us " +
                "INNER JOIN user u on u.ID = us.UserID " +
                "INNER JOIN artist a on a.ID = us.ArtistID " +
                "WHERE a.Name like ?");
        statement.setString(1, artist);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            users.add(new UserRepository(new UserContext()).getFromResultSet(rs));
        }
        return users;
    }

    @Override
    public boolean insert(String artistName) throws SQLException {
        boolean success;
        Connection con = Database.getCon();
        PreparedStatement statement = con.prepareStatement("INSERT INTO artist (Name) values (?)");
        statement.setString(1, artistName);
        success = statement.execute();
        return success;
    }

    @Override
    public boolean delete(String artistName) throws SQLException {
        boolean success;
        Connection con = Database.getCon();
        PreparedStatement statement = con.prepareStatement("DELETE FROM artist WHERE Name = ?");
        statement.setString(1, artistName);
        success = statement.execute();
        return success;
    }
}
