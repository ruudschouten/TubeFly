import main.api.Youtube;
import org.junit.Assert;
import org.junit.Test;

public class YoutubeTest {
    private String youtubeTitle = "Emmanuel & The Fear - Jimme's Song";
    private String youtubeURL = "https://www.youtube.com/watch?v=eu2I72CrLl4"; //Emmanuel & The Fear - Jimme's Song

    @Test
    public void getTitle() {
        String title = Youtube.getTitle(youtubeURL);
        Assert.assertEquals(title, youtubeTitle);
    }
}
