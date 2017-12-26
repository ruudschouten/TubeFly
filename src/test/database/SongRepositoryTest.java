package database;

import database.data.ISongContext;
import database.logic.SongContext;
import database.repositories.SongRepository;
import org.junit.Before;

public class SongRepositoryTest {
    SongRepository repo;
    ISongContext context;

    @Before
    public void createRepo() {
        context = new SongContext();
        repo = new SongRepository(context);
    }
}
