package play;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Unit test for Playlist class
 */
public class PlaylistTest {
    private User creator;
    private Song song1;
    private Song song2;
    private String name = "Name";
    private String desc = "Description";

    /**
     * Sets up for unit tests.
     * Creates a user which will create all playlists
     */
    @Before
    public void setUp() {
        creator = new User("Creator", "mail@mail.com", "Yes", "Address", Gender.MALE);

        song1 = new Song("https://www.youtube.com/watch?v=eu2I72CrLl4"); //Emmanuel & The Fear - Jimme's Song
        song2 = new Song("https://www.youtube.com/watch?v=FTQbiNvZqaY"); //Toto - Africa
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
        Assert.assertEquals(false, p.isShuffling());
        p.toggleShuffle();
        Assert.assertEquals(true, p.isShuffling());
    }

    /**
     * Toggles loop
     *
     * @throws RemoteException the exception which can be thrown by RMI
     */
    @Test
    public void toggleLoop() throws RemoteException {
        Playlist p = new Playlist(name, creator);
        Assert.assertEquals(false, p.isLooping());
        p.toggleLoop();
        Assert.assertEquals(true, p.isLooping());
    }

    /**
     * Adds a song and gets size
     *
     * @throws RemoteException the exception which can be thrown by RMI
     */
    @Test
    public void addAndGetSong() throws RemoteException {
        Playlist p = new Playlist(name, creator);
        p.addSong(song1);
        Assert.assertEquals(1, p.getSongs().size());
    }

    @Test
    public void addWithSongs() throws RemoteException {
        ArrayList<Song> songs = new ArrayList<>();
        songs.add(song1);
        songs.add(song2);
        UUID id = UUID.randomUUID();
        Playlist p = new Playlist(id.toString(), name, desc, creator, songs, new ArrayList<>());
        Assert.assertEquals(p.getId(), id);
        Assert.assertEquals(p.getCreator(), creator);
        Assert.assertEquals(p.getSongs(), songs);
        Assert.assertEquals(p.getSongs("Toto").get(0), song2);
        Assert.assertEquals(p.getSongs("Jimme's").get(0), song1);
    }

    @Test
    public void getLength() {
        DateTime d = new DateTime(480);
        ArrayList<Song> songs = new ArrayList<>();
        song1.setLength(new DateTime(206));
        song2.setLength(new DateTime(274));
        songs.add(song1);
        songs.add(song2);
        UUID id = UUID.randomUUID();
        Playlist p = new Playlist(id.toString(), name, desc, creator, songs, new ArrayList<>());
        Assert.assertEquals(p.getLength(), d);
        Assert.assertEquals(p.getLengthString(), DateTimeFormat.forPattern("mm:ss").print(d));
    }


    @Test
    public void getFollowers() {
        ArrayList<Song> songs = new ArrayList<>();
        Song s1 = new Song("https://www.youtube.com/watch?v=eu2I72CrLl4");
        songs.add(s1);
        UUID id = UUID.randomUUID();
        Playlist p = new Playlist(id.toString(), name, desc, creator, songs, new ArrayList<>());
        p.addFollower(creator);
        Assert.assertEquals(p.getFollowers().get(0), creator);
        p.removeFollower(creator);
        Assert.assertEquals(0, p.getFollowers().size());
    }
}