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
import java.util.Objects;
import java.util.UUID;

public class MockPlaylistContext implements IPlaylistContext {
    private ArrayList<Playlist> playlists = new ArrayList<>();
    private boolean initialized = false;

    private void initialize() throws RemoteException {
        ArrayList<Song> songs = new ArrayList<>();
        songs.add(new Song("https://www.youtube.com/watch?v=eu2I72CrLl4"));
        UUID id = UUID.randomUUID();
        User user = new User("Henk", "Henk@mail.com", "12345", "Address", Gender.MALE);
        Playlist p = new Playlist(id.toString(), "Best", "Songs I like", user, songs);
        playlists.add(p);
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
    public List<Playlist> getFromCreator(User user) throws SQLException, RemoteException {
        if(!initialized) initialize();
        ArrayList<Playlist> lists = new ArrayList<>();
        for (Playlist p : playlists) {
            if(p.getCreator().equals(user)) {
                lists.add(p);
            }
        }
        return null;
    }

    @Override
    public boolean insert(Playlist playlist) throws SQLException, RemoteException {
        if(!initialized) initialize();
        return playlists.add(playlist);
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
