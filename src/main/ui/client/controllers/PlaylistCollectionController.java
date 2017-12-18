package main.ui.client.controllers;

import javafx.stage.Stage;
import main.play.User;

public class PlaylistCollectionController {

    private User user;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
