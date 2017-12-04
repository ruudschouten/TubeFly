package main.play;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Playlist extends UnicastRemoteObject implements Serializable {
    private int id;
    private String name;
    private String description;
    private User creator;

    private boolean shuffle;
    private boolean loop;

    public Playlist(String name) throws RemoteException {
        setName(name);
    }
    public Playlist(String name, String description) throws RemoteException {
        setName(name);
        setDescription(description);
    }

    public int getId() {
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

    public void ToggleShuffle() {
        shuffle = !shuffle;
    }

    public void ToggleLoop() {
        loop = !loop;
    }
}
