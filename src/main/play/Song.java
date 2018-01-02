package play;

import api.YouTube;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;

import java.io.Serializable;

public class Song implements ISong, Serializable {
    private int id;
    private String youtubeURL;
    private String name;
    private String artist;
    private DateTime dateTime;

    public Song(String youtubeURL) {
        this.youtubeURL = youtubeURL;
        YouTube youTube = new YouTube(youtubeURL);
        name = youTube.getName();
        artist = youTube.getArtist();
    }

    public Song(int id, String youtubeURL, String name, String artist) {
        this.id = id;
        this.youtubeURL = youtubeURL;
        this.name = name;
        this.artist = artist;
        dateTime = new DateTime(90);
    }

    @Override
    public String getURL() {
        return youtubeURL;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getLengthString() {
        return DateTimeFormat.forPattern("mm:ss").print(dateTime);
    }

    public void setLength(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public DateTime getLength() {
        return dateTime;
    }
}
