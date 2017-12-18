package main.play;

import main.api.YouTube;

import java.io.Serializable;

public class Song implements ISong, Serializable {
    private String youtubeURL;
    private String name;
    private String artist;

    public Song(String youtubeURL) {
        this.youtubeURL = youtubeURL;
        YouTube youTube = new YouTube(youtubeURL);
        name = youTube.getName();
        artist = youTube.getArtist();
    }

    @Override
    public String getURL() {
        return youtubeURL;
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
