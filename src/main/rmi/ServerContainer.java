package rmi;

import database.logic.MockPlaylistContext;
import database.logic.MockUserContext;
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
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class ServerContainer extends UnicastRemoteObject implements IContainer {
    private RemotePublisher publisher;

    Logger logger;

    UserRepository userRepo;
    PlaylistRepository playlistRepo;

    ArrayList<User> activeUsers = new ArrayList<>();
    List<Playlist> playlists = null;

    public ServerContainer(RemotePublisher publisher) throws RemoteException {
        this.publisher = publisher;
        logger = new Logger("ServerContainer", Level.ALL, Level.ALL);
//        Change this to DatabaseContext
        userRepo = new UserRepository(new MockUserContext());
        playlistRepo = new PlaylistRepository(new MockPlaylistContext());
        playlists = playlistRepo.getAll();
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
            //TODO: Check if this works
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
        logger.log(Level.INFO, String.format("Couldn't find $s in activeUsers", user.getName()));
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
        return playlists;
    }

    @Override
    public List<Playlist> getPlaylists(int limit) {
        if(limit >= playlists.size()) return getPlaylists();
        return playlists.subList(playlists.size() - limit, playlists.size());
    }

    @Override
    public List<Playlist> getPlaylists(String searchCriteria) {
        ArrayList<Playlist> playlists = new ArrayList<>();
        for (Playlist p : this.playlists) {
            if(p.getName().contains(searchCriteria)) {
                playlists.add(p);
            }
        }
        if(playlists.size() == 0) return this.playlists;
        return playlists;
    }

    @Override
    public Playlist getPlaylist(UUID id) {
        return playlistRepo.getById(id);
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
    public boolean addSong(Playlist playlist, Song song) throws RemoteException {
        boolean success = playlist.addSong(song);
        logger.log(Level.INFO, String.format("%s added to Playlist ID: %s", song.getName(), playlist.getId()));
        publisher.inform(playlist.getId().toString(), null, playlist);
        return success;
    }

    @Override
    public boolean removeSong(Playlist playlist, Song song) {
        return false;
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
