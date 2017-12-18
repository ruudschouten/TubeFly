package main.database.repositories;

import main.database.data.IArtistContext;
import main.database.data.IUserContext;
import main.database.logic.ArtistContext;
import main.play.User;

import java.util.List;

public class ArtistRepository implements IArtistContext {
    ArtistContext context = new ArtistContext();
    @Override
    public List<User> getSubscribersAll(String artist) {
        return context.getSubscribersAll(artist);
    }

    @Override
    public void insert(String artistName) {
        context.insert(artistName);
    }

    @Override
    public void delete(String artistName) {
        context.delete(artistName);
    }
}
