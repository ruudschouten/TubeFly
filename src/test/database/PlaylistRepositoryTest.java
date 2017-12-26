package database;

import database.data.IPlaylistContext;
import database.logic.PlaylistContext;
import database.repositories.PlaylistRepository;
import org.junit.Before;

public class PlaylistRepositoryTest {
    PlaylistRepository repo;
    IPlaylistContext context;

    @Before
    public void createRepo() {
        context = new PlaylistContext();
        repo = new PlaylistRepository(context);
    }
}
