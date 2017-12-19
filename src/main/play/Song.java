package play;

import api.YouTube;

import java.io.Serializable;

public class Song implements ISong, Serializable {
    private int id;
    private String youtubeURL;
    private String name;
    private String artist;

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
    
}
