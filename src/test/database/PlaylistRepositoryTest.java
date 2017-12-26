package database;

import database.data.IPlaylistContext;
import database.logic.DatabasePlaylistContext;
import database.repositories.PlaylistRepository;
import org.junit.Before;

public class PlaylistRepositoryTest {
    PlaylistRepository repo;
    IPlaylistContext context;

    @Before
    public void createRepo() {
        context = new DatabasePlaylistContext();
        repo = new PlaylistRepository(context);
    }
}
