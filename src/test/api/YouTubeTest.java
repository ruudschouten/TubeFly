package api;

import api.YouTube;
import com.sun.xml.internal.ws.policy.AssertionSet;
import org.junit.Assert;
import org.junit.Test;
import play.Playlist;

import java.io.IOException;
import java.rmi.RemoteException;

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
    public void setFakeURL() throws IOException {
        YouTube yt = new YouTube("haha hallo");
        Assert.assertEquals(null, yt.getTitleFromURL(null));
    }
}
