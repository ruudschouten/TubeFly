package rmi;

import fontyspublisher.IRemotePropertyListener;
import fontyspublisher.IRemotePublisherForListener;
import javafx.application.Platform;
import log.Logger;
import play.Playlist;
import play.Song;
import play.User;
import ui.Message;
import ui.client.controllers.IController;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;

public class ClientContainer extends UnicastRemoteObject implements IRemotePropertyListener {
    private transient Logger logger;
    private IRemotePublisherForListener publisher;

    transient IContainer server;
    transient IController controller;

    private Playlist selectedPlaylist;
    private User user;

    private boolean loggedIn = false;

    public ClientContainer(IRemotePublisherForListener publisher, IContainer server) throws RemoteException {
        logger = new Logger("ClientContainer", Level.ALL, Level.SEVERE);
        this.publisher = publisher;
        this.server = server;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        if (Objects.equals(evt.getPropertyName(), selectedPlaylist.getId().toString())) {
            selectedPlaylist = (Playlist) evt.getNewValue();
        }
        if(evt.getPropertyName().endsWith("follow")) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Message.show("Playlist updated" ,(String) evt.getNewValue());
                }
            });
        }
        if (controller != null) controller.update();
    }

    public User getUser() {
        return user;
    }

    public void setSelectedPlaylist(Playlist playlist) {
        try {
            this.selectedPlaylist = playlist;
            if(playlist == null) return;
            server.registerProperty(playlist);
            publisher.subscribeRemoteListener(this, playlist.getId().toString());
        } catch (RemoteException e) {
            logger.logE(Level.SEVERE, e);
        }
    }

    public Playlist getSelectedPlaylist() {
        return selectedPlaylist;
    }

    public void setController(IController controller) {
        this.controller = controller;
    }

    public User register(User user) {
        try {
            Boolean success = server.register(user);
            if (success) {
                login(user.getMail(), user.getPassword());
                return user;
            }
        } catch (RemoteException e) {
            logger.logE(Level.SEVERE, e);
        }
        return null;
    }

    public User login(String mail, String password) {
        try {
            user = server.login(mail, password);
            if (user != null) {
                loggedIn = true;
                logger.log(Level.FINE, String.format("%s logged in", user.getName()));
                followPlaylists();
            } else {
                logger.log(Level.WARNING, "Login details don't match");
            }
        } catch (SQLException | RemoteException e) {
            logger.logE(Level.SEVERE, e);
        }
        return user;
    }

    private void followPlaylists() {
        try {
            for (Playlist playlist : server.getPlaylistsByFollower(user)) {
                follow(playlist);
            }
        } catch (RemoteException | SQLException e) {
            logger.logE(Level.SEVERE, e);
        }
    }

    public boolean logout() {
        try {
            if (loggedIn) {
                logger.log(Level.FINE, String.format("%s logged out", user.getName()));
                return server.logout(user);
            }
            logger.log(Level.WARNING, "User isn't logged in");
        } catch (RemoteException e) {
            logger.logE(Level.SEVERE, e);
        }
        return false;
    }

    public List<Playlist> getPlaylists() {
        try {
            return server.getPlaylists();
        } catch (RemoteException e) {
            logger.logE(Level.SEVERE, e);
        }
        return new ArrayList<>();
    }

    public List<Playlist> getPlaylists(int limit) {
        try {
            return server.getPlaylists(limit);
        } catch (RemoteException e) {
            logger.logE(Level.SEVERE, e);
        }
        return new ArrayList<>();
    }

    public Playlist getPlaylist(UUID id) {
        try {
            return server.getPlaylist(id);
        } catch (RemoteException e) {
            logger.logE(Level.SEVERE, e);
        }
        return null;
    }

    public List<Playlist> getPlaylists(String searchCriteria) {
        try {
            return server.getPlaylists(searchCriteria);
        } catch (RemoteException | SQLException e) {
            logger.logE(Level.SEVERE, e);
        }
        return new ArrayList<>();
    }

    public List<Playlist> getPlaylistsByUser() {
        try {
            return server.getPlaylists(user);
        } catch (RemoteException e) {
            logger.logE(Level.SEVERE, e);
        }
        return new ArrayList<>();
    }

    public boolean uploadPlaylist(Playlist playlist) {
        try {
            return server.uploadPlaylist(playlist);
        } catch (RemoteException e) {
            logger.logE(Level.SEVERE, e);
        }
        return false;
    }

    public boolean updatePlaylist(String name, String description) {
        try {
            return server.updatePlaylist(selectedPlaylist, name, description);
        } catch (RemoteException | SQLException e) {
            logger.logE(Level.SEVERE, e);
        }
        return false;
    }

    public boolean addSong(Playlist playlist, Song song) {
        try {
            return server.addSong(playlist, song);
        } catch (RemoteException | SQLException e) {
            logger.logE(Level.SEVERE, e);
        }
        return false;
    }

    public boolean removeSong(Playlist playlist, Song song) {
        try {
            return server.removeSong(playlist, song);
        } catch (RemoteException | SQLException e) {
            logger.logE(Level.SEVERE, e);
        }
        return false;
    }

    public boolean follow(Playlist playlist) {
        try {
            publisher.subscribeRemoteListener(this, playlist.getFollowId());
            logger.log(Level.FINE, String.format("%s started following %s", user.getName(), playlist.getName()));
            return server.follow(playlist, user);
        } catch (RemoteException | SQLException e) {
            logger.logE(Level.SEVERE, e);
        }
        return false;
    }

    public boolean unfollow(Playlist playlist) {
        try {
            publisher.unsubscribeRemoteListener(this, playlist.getFollowId());
            logger.log(Level.FINE, String.format("%s stopped following %s", user.getName(), playlist.getName()));
            return server.unfollow(playlist, user);
        } catch (RemoteException | SQLException e) {
            logger.logE(Level.SEVERE, e);
        }
        return false;
    }

    public SessionManager.SessionStatus getSessionStatus() {
        try {
            return server.getSessionStatus();
        } catch (RemoteException e) {
            logger.logE(Level.SEVERE, e);
        }
        return SessionManager.SessionStatus.DEFAULT;
    }
}
