package ui.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import play.User;
import ui.UITools;

public class EditPlaylistController {
    @FXML private TextField tbSearch;
    @FXML private TextField tbSong;

    private UITools.UIManager uiManager;

    public void initialize() {
        uiManager = new UITools.UIManager();
    }

    public void addSong(ActionEvent actionEvent) {
    }

    public void savePlaylist(ActionEvent actionEvent) {
    }
}
