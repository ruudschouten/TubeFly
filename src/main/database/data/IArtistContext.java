package main.database.data;

import main.play.User;

import java.util.List;
import java.util.UUID;

public interface IArtistContext {
    List<User> getSubscribersAll(String artist);
    void insert(String artistName);
    void delete(String artistName);
}
