package play;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import play.Song;
import org.junit.Assert;
import org.junit.Test;

public class SongTest {
    private String youtubeURL = "https://www.youtube.com/watch?v=eu2I72CrLl4"; //Emmanuel & The Fear - Jimme's Song
    private String youtubeTitle = "Emmanuel & The Fear - Jimme's Song";
    private String name = "name";
    private String artist = "artist";
    @Test
    public void getURL() {
        Song song = new Song(youtubeURL);
        Assert.assertEquals(song.getURL(), youtubeURL);
    }

    @Test
    public void getName() {
        Song song = new Song(youtubeURL);
        String name = "Jimme's Song";
        Assert.assertEquals(song.getName(), name);
    }

    @Test
    public void setName() {
        Song song = new Song(youtubeURL);
        song.setName(name);
        Assert.assertEquals(song.getName(), name);
    }

    @Test
    public void getArtist() {
        Song song = new Song(youtubeURL);
        String artist = "Emmanuel & The Fear";
        Assert.assertEquals(song.getArtist(), artist);
    }

    @Test
    public void setArtist() {
        Song song = new Song(youtubeURL);
        song.setArtist(artist);
        Assert.assertEquals(song.getArtist(), artist);
    }
    @Test
    public void getId() {
        Song song = new Song(2, youtubeURL, name, artist);
        Assert.assertEquals(song.getId(), 2);
        Assert.assertEquals(song.getName(), name);
        Assert.assertEquals(song.getArtist(), artist);
    }
    @Test
    public void getLength() {
        DateTime d = new DateTime(90);
        Song song = new Song(2, youtubeURL, name, artist);
        song.setLength(d);
        Assert.assertEquals(song.getLength(), d);
        Assert.assertEquals(song.getLengthString(), DateTimeFormat.forPattern("mm:ss").print(d));
    }
}
