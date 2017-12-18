package main.database.logic;

import main.database.data.IArtistContext;
import main.play.User;

import java.util.List;

public class ArtistContext implements IArtistContext{
    @Override
    public List<User> getSubscribersAll(String artist) {
        return null;
    }

    @Override
    public void insert(String artistName) {

    }

    @Override
    public void delete(String artistName) {

    }
}
