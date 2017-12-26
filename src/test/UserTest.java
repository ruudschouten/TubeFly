import play.Gender;
import play.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class UserTest {
    private String name = "Name";
    private String mail = "Mail@mail.com";
    private String pass = "Pass";
    private String address = "Address";
    private Gender gender = Gender.MALE;


    /**
     * Gets Id from user
     * Hard to test, since it generated a random UUID
     */
    @Test
    public void getId() {
        UUID id = UUID.randomUUID();
        User u = new User(id.toString(), name, mail, pass, address, gender);
        Assert.assertEquals(u.getId(), id);
    }

    /**
     * Gets name from user
     */
    @Test
    public void getName() {
        User u = new User(name, mail, pass, address, gender);
        Assert.assertEquals(u.getName(), name);
    }
    /**
     * Gets mail from user
     */
    @Test
    public void getMail() {
        User u = new User(name, mail, pass, address, gender);
        Assert.assertEquals(u.getMail(), mail);
    }

    /**
     * Gets password from user
     */
    @Test
    public void getPassword() {
        User u = new User(name, mail, pass, address, gender);
        Assert.assertEquals(u.getPassword(), pass);
    }

    /**
     * Gets address from user
     */
    @Test
    public void getAddress() {
        User u = new User(name, mail, pass, address, gender);
        Assert.assertEquals(u.getAddress(), address);
    }

    /**
     * Gets gender from user
     */
    @Test
    public void getGender() {
        User u = new User(name, mail, pass, address, gender);
        Assert.assertEquals(u.getGender(), gender);
    }
}
