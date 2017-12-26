package database;

import database.data.IUserContext;
import database.logic.DatabaseUserContext;
import database.repositories.UserRepository;
import org.junit.Before;

public class UserRepositoryTest {
    UserRepository repo;
    IUserContext context;

    @Before
    public void createRepo() {
        context = new DatabaseUserContext();
        repo = new UserRepository(context);
    }
}
