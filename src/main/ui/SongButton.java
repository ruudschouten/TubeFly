package ui;

import javafx.scene.control.Button;
import play.Song;

public class SongButton extends Button {
    private Song song;

    public SongButton(Song song){
        this.song = song;
    }

    public Song getSong() {
        return song;
    }
}
