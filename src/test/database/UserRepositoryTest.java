package database;

import database.data.IPlaylistContext;
import database.data.IUserContext;
import database.logic.PlaylistContext;
import database.logic.UserContext;
import database.repositories.PlaylistRepository;
import database.repositories.UserRepository;
import org.junit.Before;

public class UserRepositoryTest {
    UserRepository repo;
    IUserContext context;

    @Before
    public void createRepo() {
        context = new UserContext();
        repo = new UserRepository(context);
    }
}
