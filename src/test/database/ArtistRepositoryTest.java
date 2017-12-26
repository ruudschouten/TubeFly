package database;

import database.data.IArtistContext;
import database.logic.DatabaseArtistContext;
import database.repositories.ArtistRepository;
import org.junit.Before;

public class ArtistRepositoryTest {
    ArtistRepository repo;
    IArtistContext context;

    @Before
    public void createRepo() {
        context = new DatabaseArtistContext();
        repo = new ArtistRepository(context);
    }
}
