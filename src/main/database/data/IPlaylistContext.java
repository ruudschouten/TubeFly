package database.data;

import play.Playlist;
import play.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface IPlaylistContext {
    Playlist getById(UUID id) throws SQLException, RemoteException;
    List<Playlist> getAll() throws SQLException, RemoteException;
    List<Playlist> getFromCreator(User user) throws SQLException, RemoteException;
    List<User> getFollowers(UUID id) throws SQLException, RemoteException;
    boolean insert(Playlist playlist) throws SQLException, RemoteException;
    boolean follow(Playlist playlist, User user) throws SQLException, RemoteException;
    boolean unfollow(Playlist playlist, User user) throws SQLException, RemoteException;
    boolean delete(UUID id) throws SQLException, RemoteException;
    Playlist getFromResultSet(ResultSet rs) throws SQLException, RemoteException;
}
