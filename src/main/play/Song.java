package main.play;

import main.api.Youtube;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.Serializable;

public class Song implements ISong, Serializable {
    private String youtubeURL;
    private String name;
    private String artist;

    private boolean isPlaying;
    private boolean isPaused;

    public Song(String youtubeURL) {
        this.youtubeURL = youtubeURL;
        name = Youtube.getTitle(youtubeURL);
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

    public boolean isPlaying() {
        return isPlaying;
    }

    public boolean isPaused() {
        return isPaused;
    }

//    Move this to Playlist
//    public void Play() {
//        throw new NotImplementedException();
//    }
//
//    public void Pause() {
//        throw new NotImplementedException();
//    }
//
//    public void Stop() {
//        throw new NotImplementedException();
//    }
}
