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
        Assert.assertEquals(artist, song.getArtist());
    }

    @Test
    public void getLength() {
        DateTime d = new DateTime(90);
        Song song = new Song(youtubeURL, name, artist);
        song.setLength(d);
        Assert.assertEquals(d, song.getLength());
        Assert.assertEquals(DateTimeFormat.forPattern("mm:ss").print(d), song.getLengthString());
    }

    @Test
    public void getCurrentTime() {
        Song song = new Song(youtubeURL, name, artist);
        Assert.assertEquals(new DateTime(0), song.getCurrentTime());
    }

    @Test
    public void getEquals() {
        Song song = new Song(youtubeURL, name, artist);
        Song song1 = new Song(youtubeURL, name, artist);
        Song song2 = new Song("https://www.youtube.com/watch?v=FTQbiNvZqaY", name, artist);
        Assert.assertEquals(true, song.equals(song1));
    }
}
