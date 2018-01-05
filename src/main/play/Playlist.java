package play;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Playlist implements Serializable {
    private UUID id;
    private String name;
    private String description;
    private User creator;

    private List<Song> songs;
    private List<User> followers;

    private boolean shuffle;
    private boolean loop;

    public Playlist(String name, User creator) {
        setName(name);
        this.creator = creator;
        id = UUID.randomUUID();
        songs = new ArrayList<>();
        followers = new ArrayList<>();
    }

    public Playlist(String name, String description, User creator) {
        setName(name);
        setDescription(description);
        id = UUID.randomUUID();
        this.creator = creator;
        songs = new ArrayList<>();
        followers = new ArrayList<>();
    }

    public Playlist(String id, String name, String description, User creator, List<Song> songs, List<User> followers) {
        setName(name);
        setDescription(description);
        this.id = UUID.fromString(id);
        this.creator = creator;
        this.songs = songs;
        this.followers = followers;
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

    public boolean addSong(Song song) {
        return songs.add(song);
    }

    public boolean removeSong(Song song) {
        return songs.remove(song);
    }

    public DateTime getLength() {
        long time = 0;
        for (Song s : this.songs) {
             time += s.getLength().getMillis();
        }
        return new DateTime(time);
    }

    public String getLengthString() {
        return DateTimeFormat.forPattern("mm:ss").print(getLength());
    }

    public List<Song> getSongs(String searchCriteria) {
        ArrayList<Song> filteredSongs = new ArrayList<>();
        for (Song s : songs) {
            if (s.getName().contains(searchCriteria) || s.getArtist().contains(searchCriteria)) {
                filteredSongs.add(s);
            }
        }
        if (!filteredSongs.isEmpty()) return songs;
        return filteredSongs;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public boolean addFollower(User user) {
        return followers.add(user);
    }
    public boolean removeFollower(User user) {
        return followers.remove(user);
    }
}
