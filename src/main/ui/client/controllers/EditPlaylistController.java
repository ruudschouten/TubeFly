package ui.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import play.User;

public class EditPlaylistController {
    @FXML private TextField tbSearch;
    @FXML private TextField tbSong;

    private User user;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addSong(ActionEvent actionEvent) {
    }

    public void savePlaylist(ActionEvent actionEvent) {
    }
}
