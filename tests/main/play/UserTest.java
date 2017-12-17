package main.play;

import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class UserTest {
    private String name = "Name";
    private String pass = "Pass";
    private String address = "Address";
    private Gender gender = Gender.MALE;


    /**
     * Gets Id from user
     * Hard to test, since it generated a random UUID
     */
    @Test
    public void getId() {
        User u = new User(name, pass, address, gender);
        Assert.assertNotEquals(u.getId(), UUID.randomUUID());
    }

    /**
     * Gets name from user
     */
    @Test
    public void getName() {
        User u = new User(name, pass, address, gender);
        Assert.assertEquals(u.getName(), name);
    }
    /**
     * Gets password from user
     */
    @Test
    public void getPassword() {
        User u = new User(name, pass, address, gender);
        Assert.assertEquals(u.getPassword(), pass);
    }

    /**
     * Gets address from user
     */
    @Test
    public void getAddress() {
        User u = new User(name, pass, address, gender);
        Assert.assertEquals(u.getAddress(), address);
    }

    /**
     * Gets gender from user
     */
    @Test
    public void getGender() {
        User u = new User(name, pass, address, gender);
        Assert.assertEquals(u.getGender(), gender);
    }
}
