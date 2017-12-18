package main.ui.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.play.User;

public class MenuController {
    @FXML private TextField tbSearch;

    private User user;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void filterPlaylists(ActionEvent actionEvent) {
    }
}
