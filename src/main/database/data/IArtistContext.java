package main.database.data;

import main.play.User;

import java.sql.SQLException;
import java.util.List;

public interface IArtistContext {
    String getFromId(int id) throws SQLException;
    int getFromName(String name) throws SQLException;
    List<User> getSubscribersAll(String artist) throws SQLException;
    boolean insert(String artistName) throws SQLException;
    boolean delete(String artistName) throws SQLException;
}
