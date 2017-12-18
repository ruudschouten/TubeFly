package main.ui.client.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.play.User;
import main.ui.UITools;

public class LoginController {
    @FXML private TextField tbMail;
    @FXML private PasswordField tbPassword;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void showMain(ActionEvent actionEvent) {
        //TODO: Check login details

        UITools.UIManager uiManager = new UITools.UIManager();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                uiManager.loadFXML("main/resources/playlistcollection.fxml", "Playlists");
            }
        });
    }

    public void showRegUser(MouseEvent mouseEvent) {
        UITools.UIManager uiManager = new UITools.UIManager();
        uiManager.loadFXML("main/resources/register.fxml", "Playlists");
    }
}
