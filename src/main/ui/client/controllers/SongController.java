package ui.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import play.Playlist;
import play.Song;
import rmi.ClientContainer;
import ui.ResourceHandler;

public class SongController {
    @FXML private TextField tbURL;
    @FXML private TextField tbArtist;
    @FXML private TextField tbSong;

    private Song currentSong;
    private Playlist playlist;
    private ClientContainer container;

    public void initialize() {
        container = ResourceHandler.getContainer();
        playlist = container.getSelectedPlaylist();
        tbURL.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue) {
                generateSongInfo();
            }
        });
    }

    private void generateSongInfo() {
        currentSong = new Song(tbURL.getText());
        tbArtist.setText(currentSong.getArtist());
        tbSong.setText(currentSong.getName());
    }

    public void generateInfo(ActionEvent actionEvent) {
        generateSongInfo();
    }

    public void addSongToPlaylist(ActionEvent actionEvent) {
        currentSong.setArtist(tbArtist.getText());
        currentSong.setName(tbSong.getText());
        container.addSong(playlist, currentSong);
        Stage stage = (Stage) tbArtist.getScene().getWindow();
        stage.close();
    }
}
