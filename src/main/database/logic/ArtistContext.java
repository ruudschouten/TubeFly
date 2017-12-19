package database.logic;

import database.data.Database;
import database.data.IArtistContext;
import database.repositories.UserRepository;
import play.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArtistContext implements IArtistContext {
    @Override
    public String getFromId(int id) throws SQLException {
        String name = "";
        PreparedStatement statement = null;
        try {
            statement = Database.getCon().prepareStatement("SELECT Name FROM artist WHERE ID = ?");
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    name = rs.getString("Name");
                }
            }
        } finally {
            if (statement != null) statement.close();
        }
        return name;
    }

    @Override
    public int getFromName(String name) throws SQLException {
        int id = 0;
        PreparedStatement statement = null;
        try {
            statement = Database.getCon().prepareStatement("SELECT Name FROM artist WHERE Name LIKE ?");
            statement.setString(1, name);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    id = rs.getInt("ID");
                }
            }
        } finally {
            if (statement != null) statement.close();
        }
        return id;
    }

    @Override
    public List<User> getSubscribersAll(String artist) throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = Database.getCon().prepareStatement("SELECT u.* FROM user_subscribed us " +
                    "INNER JOIN user u ON u.ID = us.UserID " +
                    "INNER JOIN artist a ON a.ID = us.ArtistID " +
                    "WHERE a.Name LIKE ?");
            statement.setString(1, artist);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    users.add(new UserRepository(new UserContext()).getFromResultSet(rs));
                }
            }
        } finally {
            if (statement != null) statement.close();
        }
        return users;
    }

    @Override
    public boolean insert(String artistName) throws SQLException {
        boolean success;
        PreparedStatement statement = null;
        try {
            statement = Database.getCon().prepareStatement("INSERT INTO artist (Name) VALUES (?)");
            statement.setString(1, artistName);
            success = statement.execute();
        } finally {
            if (statement != null) statement.close();
        }
        return success;
    }

    @Override
    public boolean delete(String artistName) throws SQLException {
        boolean success;
        PreparedStatement statement = null;
        try {
            statement = Database.getCon().prepareStatement("DELETE FROM artist WHERE Name = ?");
            statement.setString(1, artistName);
            success = statement.execute();
        } finally {
            if (statement != null) statement.close();
        }
        return success;
    }
}
