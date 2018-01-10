package rmi;

import database.logic.DatabasePlaylistContext;
import database.logic.DatabaseUserContext;
import database.repositories.PlaylistRepository;
import database.repositories.UserRepository;
import fontyspublisher.RemotePublisher;
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
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;

public class ServerContainer extends UnicastRemoteObject implements IContainer {
    private RemotePublisher publisher;

    private transient Logger logger;

    private transient UserRepository userRepo;
    private transient PlaylistRepository playlistRepo;

    private ArrayList<User> activeUsers = new ArrayList<>();

    public ServerContainer(RemotePublisher publisher) throws RemoteException {
        this.publisher = publisher;
        logger = new Logger("ServerContainer", Level.ALL, Level.ALL);

        userRepo = new UserRepository(new DatabaseUserContext());
        playlistRepo = new PlaylistRepository(new DatabasePlaylistContext());
    }

    @Override
    public boolean register(User user) {
        return userRepo.insert(user);
    }

    @Override
    public User login(String mail, String password) throws SQLException, RemoteException {
        User u = userRepo.login(mail, password);
        if (u != null) {
            publisher.registerProperty(Properties.USER_PROPERTY);
            logger.log(Level.FINE, String.format("Added %s to activeUsers", u.getName()));
            activeUsers.add(u);
            publisher.inform(Properties.USER_PROPERTY, null, u);
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
        logger.log(Level.INFO, String.format("Couldn't find %s in activeUsers", user.getName()));
        return false;
    }

    @Override
    public boolean subscribeToArtist(User user, String artist) throws RemoteException {
        publisher.registerProperty(artist);
        throw new NotImplementedException();
    }

    @Override
    public boolean notifyUser() {
        throw new NotImplementedException();
    }

    @Override
    public List<Playlist> getPlaylists() {
        return playlistRepo.getAll();
    }

    @Override
    public List<Playlist> getPlaylists(int limit) {
        List<Playlist> playlists = playlistRepo.getAll();
        if (limit >= playlists.size()) return playlists;
        return new ArrayList<>(playlists.subList(playlists.size() - limit, playlists.size()));
    }

    @Override
    public List<Playlist> getPlaylists(User user) throws RemoteException {
        return playlistRepo.getFromCreator(user);
    }

    @Override
    public List<Playlist> getPlaylists(String searchCriteria) throws SQLException, RemoteException {
        List<Playlist> playlists = playlistRepo.getAll(searchCriteria);
        if (!playlists.isEmpty()) return playlists;
        return getPlaylists(10);
    }

    @Override
    public Playlist getPlaylist(UUID id) {
        return playlistRepo.getById(id);
    }

    @Override
    public boolean uploadPlaylist(Playlist playlist) {
        if (playlist != null) {
            return playlistRepo.insert(playlist);
        }
        return false;
    }

    @Override
    public boolean updatePlaylist(Playlist playlist, String name, String description) throws RemoteException, SQLException {
        if (name != null && !Objects.equals(name, "")) {
            playlistRepo.update(playlist.getId(), name, description);
            publisher.inform(playlist.getId().toString(), null, playlistRepo.getById(playlist.getId()));
            return true;
        }
        return false;
    }

    @Override
    public boolean addSong(Playlist playlist, Song song) throws RemoteException, SQLException {
        boolean success = playlistRepo.addSong(song, playlist.getId());
        logger.log(Level.INFO, String.format("%s added to Playlist ID: %s", song.getName(), playlist.getId()));
        publisher.inform(playlist.getId().toString(), null, playlistRepo.getById(playlist.getId()));
        return success;
    }

    @Override
    public boolean removeSong(Playlist playlist, Song song) throws RemoteException, SQLException {
        boolean success = playlistRepo.removeSong(song, playlist.getId());
        logger.log(Level.INFO, String.format("%s added to Playlist ID: %s", song.getName(), playlist.getId()));
        publisher.inform(playlist.getId().toString(), null, playlistRepo.getById(playlist.getId()));
        return success;
    }

    @Override
    public boolean follow(Playlist playlist, User user) throws SQLException, RemoteException {
        return playlistRepo.follow(playlist, user);
    }

    @Override
    public boolean unfollow(Playlist playlist, User user) throws SQLException, RemoteException {
        return playlistRepo.unfollow(playlist, user);
    }

    @Override
    public void registerProperty(Playlist playlist) throws RemoteException {
        publisher.registerProperty(playlist.getId().toString());
    }
}
