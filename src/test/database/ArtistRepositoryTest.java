package database;

import database.data.IArtistContext;
import database.logic.ArtistContext;
import database.repositories.ArtistRepository;
import org.junit.Before;

public class ArtistRepositoryTest {
    ArtistRepository repo;
    IArtistContext context;

    @Before
    public void createRepo() {
        context = new ArtistContext();
        repo = new ArtistRepository(context);
    }
}
