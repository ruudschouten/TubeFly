package rmi;

import fontyspublisher.IRemotePropertyListener;
import fontyspublisher.IRemotePublisherForListener;
import log.Logger;
import play.Playlist;
import play.Song;
import play.User;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class ClientContainer extends UnicastRemoteObject implements IRemotePropertyListener {
    private Logger logger;
    private IRemotePublisherForListener publisher;

    IContainer server;

    private User user;

    private boolean loggedIn = false;

    public ClientContainer(IRemotePublisherForListener publisher, IContainer server) throws RemoteException {
        logger = new Logger("ClientContainer", Level.ALL, Level.SEVERE);
        this.publisher = publisher;
        this.server = server;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        //TODO: Figure out what to use this for
    }

    public User register(User user) {
        if (server.register(user)) {
            login(user.getMail(), user.getPassword());
        }
        return user;
    }

    public User login(String mail, String password) {
        try {
            user = server.login(mail, password);
            if (user != null)  {
                loggedIn = true;
                logger.log(Level.FINE, String.format("%s logged in", user.getName()));
            } else {
                logger.log(Level.WARNING, "Login details don't match");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.toString());
        }
        return user;
    }

    public boolean logout() {
        if (loggedIn) {
            logger.log(Level.FINE, String.format("%s logged out", user.getName()));
            return server.logout(user);
        }
        logger.log(Level.WARNING, "User isn't logged in");
        return false;
    }

    public boolean subscribeToArtist(String artist) throws RemoteException {
        publisher.subscribeRemoteListener(this, Properties.ARTIST_PROPERTY);
        logger.log(Level.FINE, String.format("%s subscribed to artist:%s", user.getName(), artist));
        return server.subscribeToArtist(user, artist);
    }

    //TODO: Figure out how to call this
    public boolean notifyUser() {
        return server.notifyUser();
    }

    public List<Playlist> getPlaylists() {
        return server.getPlaylists();
    }

    public List<Playlist> getPlaylists(int limit) {
        return server.getPlaylists(limit);
    }

    public Playlist getPlaylist(UUID id) {
        return server.getPlaylist(id);
    }

    public Playlist getPlaylist(String searchCriteria) {
        return server.getPlaylist(searchCriteria);
    }

    public boolean uploadPlaylist(Playlist playlist) {
        return server.uploadPlaylist(playlist);
    }

    public boolean updatePlaylist(String name, String description) {
        return server.updatePlaylist(name, description);
    }

    public boolean addSong(Playlist playlist, Song song) {
        return server.addSong(playlist, song);
    }

    public boolean removeSong(Playlist playlist, Song song) {
        return server.removeSong(playlist, song);
    }

    public boolean follow(Playlist playlist) throws RemoteException {
        publisher.subscribeRemoteListener(this, Properties.PLAYLIST_PROPERTY);
        logger.log(Level.FINE, String.format("%s started following %s", user.getName(), playlist.getName()));
        return server.follow(playlist, user);
    }

    public boolean unfollow(Playlist playlist) throws RemoteException {
        publisher.unsubscribeRemoteListener(this, Properties.PLAYLIST_PROPERTY);
        logger.log(Level.FINE, String.format("%s stopped following %s", user.getName(), playlist.getName()));
        return server.unfollow(playlist, user);
    }
}
