package rmi;

import database.logic.PlaylistContext;
import database.logic.UserContext;
import database.repositories.PlaylistRepository;
import database.repositories.UserRepository;
import log.Logger;
import play.Playlist;
import play.Song;
import play.User;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class ServerContainer extends UnicastRemoteObject implements IContainer {
    Logger logger;

    UserRepository userRepo;
    PlaylistRepository playlistRepo;

    ArrayList<User> activeUsers = new ArrayList<>();
    List<Playlist> playlists = null;

    protected ServerContainer() throws RemoteException {
        logger = new Logger("ServerContainer", Level.ALL, Level.ALL);
        userRepo = new UserRepository(new UserContext());
        playlistRepo = new PlaylistRepository(new PlaylistContext());
        playlists = playlistRepo.getAll();
    }

    @Override
    public boolean register(User user) {
        return false;
    }

    @Override
    public User login(String mail, String password) throws SQLException {
        User u = userRepo.login(mail, password);
        if (u != null) {
            logger.log(Level.FINE, String.format("Added %s to activeUsers", u.getName()));
            activeUsers.add(u);
            return u;
        }
        logger.log(Level.INFO, String.format("User with %s - %s not found in database", mail, password));
        return null;
    }

    @Override
    public boolean logout(User user) {
        if (activeUsers.size() > 1) {
            logger.log(Level.FINE, String.format("Removed %s from activeUsers", user.getName()));
            return activeUsers.remove(user);
        }
        return false;
    }

    @Override
    public boolean subscribeToArtist(User user, String artist) {
        throw new NotImplementedException();
    }

    @Override
    public boolean notifyUser() {
        throw new NotImplementedException();
    }

    @Override
    public List<Playlist> getPlaylists() {
        return playlists;
    }

    @Override
    public List<Playlist> getPlaylists(int limit) {
        return playlists.subList(playlists.size() - limit, playlists.size());
    }

    @Override
    public Playlist getPlaylist(UUID id) {
        return playlistRepo.getById(id);
    }

    @Override
    public Playlist getPlaylist(String searchCriteria) {
        return null;
    }

    @Override
    public boolean uploadPlaylist(Playlist playlist) {
        if (playlist != null) {
            return playlists.add(playlist);
        }
        return false;
    }

    @Override
    public boolean updatePlaylist(String name, String description) {
        return false;
    }

    @Override
    public boolean addSong(Playlist playlist, Song song) {
        return false;
    }

    @Override
    public boolean removeSong(Playlist playlist, Song song) {
        return false;
    }

    @Override
    public boolean follow(Playlist playlist, User user) {
        return false;
    }

    @Override
    public boolean unfollow(Playlist playlist, User user) {
        return false;
    }
}
