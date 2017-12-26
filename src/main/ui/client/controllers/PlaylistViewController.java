package ui.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import play.User;
import ui.UITools;

public class PlaylistViewController {
    @FXML private TextField tbSearch;
    @FXML private Label lbSongInfo;
    @FXML private Label lbSongTime;
    @FXML private Label currentSongInfo;
    @FXML private ProgressBar songTimeProgress;

    private UITools.UIManager uiManager;

    public void initialize() {
        uiManager = new UITools.UIManager();
    }

    public void filterSongs(ActionEvent actionEvent) {
    }

    public void songPlayPause(ActionEvent actionEvent) {
    }

    public void toggleShuffle(ActionEvent actionEvent) {
    }

    public void previousSong(ActionEvent actionEvent) {
    }

    public void currentSongPlayPause(ActionEvent actionEvent) {
    }

    public void nextSong(ActionEvent actionEvent) {
    }

    public void toggleLoop(ActionEvent actionEvent) {
    }
}
