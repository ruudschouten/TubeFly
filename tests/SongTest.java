import main.play.Song;
import org.junit.Assert;
import org.junit.Test;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class SongTest {
    private String youtubeURL = "https://www.youtube.com/watch?v=eu2I72CrLl4"; //Emmanuel & The Fear - Jimme's Song
    private String name = "name";
    private String artist = "artist";
    @Test
    public void getURL() {
        Song song = new Song(youtubeURL);
        Assert.assertEquals(song.getURL(), youtubeURL);
    }

    @Test
    public void getName() {
        //TODO: Get youtubelink parsing working before testing
        throw new NotImplementedException();
    }

    @Test
    public void setName() {
        Song song = new Song(youtubeURL);
        song.setName(name);
        Assert.assertEquals(song.getName(), name);
    }

    @Test
    public void getArtist() {
        throw new NotImplementedException();
    }

    @Test
    public void setArtist() {
        Song song = new Song(youtubeURL);
        song.setArtist(artist);
        Assert.assertEquals(song.getArtist(), artist);
    }

    @Test
    public void isPlaying() {
        Song song = new Song(youtubeURL);
        Assert.assertEquals(song.isPlaying(), false);
    }

    @Test
    public void isPaused() {
        Song song = new Song(youtubeURL);
        Assert.assertEquals(song.isPaused(), false);
    }
}
