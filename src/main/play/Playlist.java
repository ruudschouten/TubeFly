package main.play;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.UUID;

public class Playlist extends UnicastRemoteObject implements Serializable {
    private UUID id;
    private String name;
    private String description;
    private User creator;

    private ArrayList<Song> songs;

    private boolean shuffle;
    private boolean loop;

    public Playlist(String name, User creator) throws RemoteException {
        setName(name);
        this.creator = creator;
        id = UUID.randomUUID();
        songs = new ArrayList<>();
    }

    public Playlist(String name, String description, User creator) throws RemoteException {
        setName(name);
        setDescription(description);
        id = UUID.randomUUID();
        this.creator = creator;
        songs = new ArrayList<>();
    }

    public Playlist(String id, String name, String description, User creator, ArrayList<Song> songs) throws RemoteException {
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

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void addSong(Song song) {
        songs.add(song);
    }
}
