package database.logic;

import database.data.IPlaylistContext;
import play.Gender;
import play.Playlist;
import play.Song;
import play.User;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MockPlaylistContext implements IPlaylistContext {
    private ArrayList<Playlist> playlists = new ArrayList<>();
    private boolean initialized = false;

    private void initialize() throws RemoteException {
        ArrayList<Song> songs = new ArrayList<>();
        songs.add(new Song("https://www.youtube.com/watch?v=eu2I72CrLl4"));
        songs.add(new Song("https://www.youtube.com/watch?v=L_jWHffIx5E"));
        songs.add(new Song("https://www.youtube.com/watch?v=PWgvGjAhvIw"));
        User user = new User("Henk", "Henk@mail.com", "12345", "Address", Gender.MALE);
        User user2 = new User("Yes", "yes", "yes", "yes", Gender.MALE);
        User user3 = new User("no", "no", "no", "no", Gender.MALE);
        ArrayList<User> followers = new ArrayList<>();
        followers.add(user2);
        followers.add(user3);
        Playlist p = new Playlist(UUID.randomUUID().toString(), "Best", "Songs I like", user, songs, new ArrayList<>());
        Playlist p2 = new Playlist(UUID.randomUUID().toString(), "Neh", "Could be better tbqh fam", user, songs, followers);
        Playlist p3 = new Playlist(UUID.randomUUID().toString(), "Berry", "Berry good", user, songs, followers);
        playlists.add(p);
        playlists.add(p2);
        playlists.add(p3);
        initialized = true;
    }

    @Override
    public Playlist getById(UUID id) throws SQLException, RemoteException {
        if(!initialized) initialize();
        for (Playlist p : playlists) {
            if(p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public List<Playlist> getAll() throws SQLException, RemoteException {
        if(!initialized) initialize();
        return playlists;
    }

    @Override
    public List<Playlist> getAll(String searchCriteria) throws SQLException, RemoteException {
        if(!initialized) initialize();
        ArrayList<Playlist> lists = new ArrayList<>();
        for (Playlist p : playlists) {
            if(p.getName().contains(searchCriteria)) {
                lists.add(p);
            }
        }
        return lists;
    }

    @Override
    public List<Playlist> getFromCreator(User user) throws SQLException, RemoteException {
        if(!initialized) initialize();
        ArrayList<Playlist> lists = new ArrayList<>();
        for (Playlist p : playlists) {
            if(p.getCreator().equals(user)) {
                lists.add(p);
            }
        }
        return lists;
    }

    @Override
    public List<User> getFollowers(UUID id) throws SQLException, RemoteException {
        for (Playlist p : playlists) {
            if(p.getId().equals(id)) {
                return p.getFollowers();
            }
        }
        return new ArrayList<>();
    }

    @Override
    public boolean addSong(Song song, UUID id) throws SQLException, RemoteException {
        for (Playlist p : playlists) {
            if(p.getId().equals(id)) {
                return p.addSong(song);
            }
        }
        return false;
    }

    @Override
    public boolean removeSong(Song song, UUID id) throws SQLException, RemoteException {
        for (Playlist p : playlists) {
            if(p.getId().equals(id)) {
                return p.removeSong(song);
            }
        }
        return false;
    }

    @Override
    public boolean insert(Playlist playlist) throws SQLException, RemoteException {
        if(!initialized) initialize();
        return playlists.add(playlist);
    }

    @Override
    public boolean update(UUID id, String name, String description) throws SQLException, RemoteException {
        for (Playlist p : playlists) {
            if(p.getId().equals(id)) {
                p.setName(name);
                p.setDescription(description);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean follow(Playlist playlist, User user) throws SQLException, RemoteException {
        return playlist.addFollower(user);
    }

    @Override
    public boolean unfollow(Playlist playlist, User user) throws SQLException, RemoteException {
        return playlist.removeFollower(user);
    }

    @Override
    public boolean delete(UUID id) throws SQLException, RemoteException {
        if(!initialized) initialize();
        return playlists.removeIf(p -> p.getId().equals(id));
    }

    @Override
    public Playlist getFromResultSet(ResultSet rs) throws SQLException, RemoteException {
        //This isn't needed for mock data
        throw new NotImplementedException();
    }
}
