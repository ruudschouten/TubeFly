package ui.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import play.User;

public class RegisterController {
    @FXML private TextField tbName;
    @FXML private ToggleGroup genderGroup;
    @FXML private RadioButton rbMale;
    @FXML private RadioButton rbFemale;
    @FXML private TextField tbMail;
    @FXML private TextField tbAdress;
    @FXML private PasswordField tbPassword;
    @FXML private PasswordField tbPasswordConfirm;

    private User user;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void register(ActionEvent actionEvent) {
    }
}
