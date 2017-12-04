package main.ui.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.play.User;

public class loginController {
    @FXML private TextField tbMail;
    @FXML private PasswordField tbPassword;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void showMain(ActionEvent actionEvent) {

//        CreatePartyView createParty = new CreatePartyView();
//        createParty.start(stage, user);
    }

    public void showRegUser(MouseEvent mouseEvent) {
    }
}
