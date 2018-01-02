package play;

import api.YouTube;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.Serializable;

public class Song implements ISong, Serializable {
    private int id;
    private String youtubeURL;
    private String name;
    private String artist;
    private DateTime length;
    private DateTime currentTime;

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
        length = new DateTime(90);
        currentTime = new DateTime(0);
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
        return DateTimeFormat.forPattern("mm:ss").print(length);
    }

    public void setLength(DateTime dateTime) {
        this.length = dateTime;
    }

    public DateTime getLength() {
        return length;
    }

    public DateTime getCurrentTime() {
        return currentTime;
    }

    @Override
    public boolean equals(Object obj) {
        return ((Song) obj).getId() == getId();
    }
}
