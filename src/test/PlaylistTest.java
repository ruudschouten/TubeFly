

import play.Gender;
import play.Playlist;
import play.Song;
import play.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Unit test for Playlist class
 */
public class PlaylistTest {
    private User creator;
    private String name = "Name";
    private String desc = "Description";

    /**
     * Sets up for unit tests.
     * Creates a user which will create all playlists
     */
    @Before
    public void setUp() {
        creator = new User("Creator", "mail@mail.com", "Yes", "Address", Gender.MALE);
    }

    /**
     * Gets Id from playlist
     * Hard to test, since it generated a random UUID
     *
     * @throws RemoteException the exception which can be thrown by RMI
     */
    @Test
    public void getId() throws RemoteException {
        Playlist p = new Playlist(name, creator);
        Assert.assertNotEquals(p.getId(), UUID.randomUUID());
    }

    /**
     * Gets name from playlist
     *
     * @throws RemoteException the exception which can be thrown by RMI
     */
    @Test
    public void getName() throws RemoteException {
        Playlist p = new Playlist(name, creator);
        Playlist pDescription = new Playlist(name, desc, creator);
        Assert.assertEquals(p.getName(), name);
        Assert.assertEquals(pDescription.getName(), name);
    }

    /**
     * Sets name for playlist
     *
     * @throws RemoteException the exception which can be thrown by RMI
     */
    @Test
    public void setName() throws RemoteException {
        Playlist p = new Playlist(name, creator);
        Playlist pDescription = new Playlist(name, desc, creator);
        String name2 = "Name 2";
        p.setName(name2);
        pDescription.setName(name2);
        Assert.assertEquals(p.getName(), name2);
        Assert.assertEquals(pDescription.getName(), name2);
    }

    /**
     * Tests setting a long name (longer than 64 chars)
     *
     * @throws RemoteException          the exception which can be thrown by RMI
     * @throws IllegalArgumentException the exception which is thrown
     */
    @Test(expected = IllegalArgumentException.class)
    public void setLongName() throws RemoteException, IllegalArgumentException {
        new Playlist("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                creator);
    }

    /**
     * Gets description from playlist
     *
     * @throws RemoteException the exception which can be thrown by RMI
     */
    @Test
    public void getDescription() throws RemoteException {
        Playlist pDescription = new Playlist(name, desc, creator);
        Assert.assertEquals(pDescription.getDescription(), desc);
    }

    /**
     * Sets description for playlist
     *
     * @throws RemoteException the exception which can be thrown by RMI
     */
    @Test
    public void setDescription() throws RemoteException {
        Playlist pDescription = new Playlist(name, desc, creator);
        String desc2 = "Description 2";
        pDescription.setDescription(desc2);
        Assert.assertEquals(pDescription.getDescription(), desc2);
    }

    /**
     * Gets creator from party
     *
     * @throws RemoteException the exception which can be thrown by RMI
     */
    @Test
    public void getCreator() throws RemoteException {
        Playlist p = new Playlist(name, creator);
        Playlist pDescription = new Playlist(name, desc, creator);
        Assert.assertEquals(p.getCreator(), creator);
        Assert.assertEquals(pDescription.getCreator(), creator);
    }

    /**
     * Toggles shuffle
     *
     * @throws RemoteException the exception which can be thrown by RMI
     */
    @Test
    public void toggleShuffle() throws RemoteException {
        Playlist p = new Playlist(name, creator);
        Assert.assertEquals(p.isShuffling(), false);
        p.toggleShuffle();
        Assert.assertEquals(p.isShuffling(), true);
    }

    /**
     * Toggles loop
     *
     * @throws RemoteException the exception which can be thrown by RMI
     */
    @Test
    public void toggleLoop() throws RemoteException {
        Playlist p = new Playlist(name, creator);
        Assert.assertEquals(p.isLooping(), false);
        p.toggleLoop();
        Assert.assertEquals(p.isLooping(), true);
    }

    /**
     * Adds a song and gets size
     *
     * @throws RemoteException the exception which can be thrown by RMI
     */
    @Test
    public void addAndGetSong() throws RemoteException {
        Playlist p = new Playlist(name, creator);
        Song s = new Song("https://www.youtube.com/watch?v=eu2I72CrLl4");
        p.addSong(s);
        Assert.assertEquals(p.getSongs().size(), 1);
    }

    @Test
    public void addWithSongs() throws RemoteException {
        ArrayList<Song> songs = new ArrayList<>();
        songs.add(new Song("https://www.youtube.com/watch?v=eu2I72CrLl4"));
        UUID id = UUID.randomUUID();
        Playlist p = new Playlist(id.toString(), name, desc, creator, songs);
        Assert.assertEquals(p.getId(), id);
        Assert.assertEquals(p.getCreator(), creator);
        Assert.assertEquals(p.getSongs(), songs);
    }
}