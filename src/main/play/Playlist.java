package play;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Playlist implements Serializable {
    private UUID id;
    private String name;
    private String description;
    private User creator;

    private List<Song> songs;

    private boolean shuffle;
    private boolean loop;

    public Playlist(String name, User creator) {
        setName(name);
        this.creator = creator;
        id = UUID.randomUUID();
        songs = new ArrayList<>();
    }

    public Playlist(String name, String description, User creator) {
        setName(name);
        setDescription(description);
        id = UUID.randomUUID();
        this.creator = creator;
        songs = new ArrayList<>();
    }

    public Playlist(String id, String name, String description, User creator, List<Song> songs) {
        setName(name);
        setDescription(description);
        this.id = UUID.fromString(id);
        this.creator = creator;
        this.songs = songs;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.length() > 64) {
            throw new IllegalArgumentException("Name length is too big");
        } else {
            this.name = name;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getCreator() {
        return creator;
    }

    public boolean isShuffling() {
        return shuffle;
    }

    public boolean isLooping() {
        return loop;
    }

    public void toggleShuffle() {
        shuffle = !shuffle;
    }

    public void toggleLoop() {
        loop = !loop;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public List<Song> getSongs(String searchCriteria) {
        ArrayList<Song> songs = new ArrayList<>();
        for (Song s : this.songs) {
            if (s.getName().contains(searchCriteria)) {
                songs.add(s);
            } else if (s.getArtist().contains(searchCriteria)) {
                songs.add(s);
            }
        }
        if (songs.size() == 0) return this.songs;
        return songs;
    }
}
