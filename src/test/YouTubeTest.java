import api.YouTube;
import org.junit.Assert;
import org.junit.Test;

public class YouTubeTest {
    private String youtubeTitle = "Emmanuel & The Fear - Jimme's Song";
    private String youtubeURL = "https://www.youtube.com/watch?v=eu2I72CrLl4"; //Emmanuel & The Fear - Jimme's Song

    @Test
    public void getTitle() {
        YouTube yt = new YouTube(youtubeURL);
        String title = yt.getTitle();
        Assert.assertEquals(title, youtubeTitle);
    }

    @Test
    public void getName() {
        YouTube yt = new YouTube(youtubeURL);
        String name = "Jimme's Song";
        Assert.assertEquals(name, yt.getName());
    }
    @Test
    public void getArtist() {
        YouTube yt = new YouTube(youtubeURL);
        String artist = "Emmanuel & The Fear";
        Assert.assertEquals(artist, yt.getArtist());
    }
}
