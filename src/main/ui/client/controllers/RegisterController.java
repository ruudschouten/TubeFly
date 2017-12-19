package ui.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import play.Gender;
import play.User;
import ui.Message;
import ui.UITools;

import java.util.Objects;

public class RegisterController {
    @FXML private TextField tbName;
    @FXML private ToggleGroup genderGroup;
    @FXML private TextField tbMail;
    @FXML private TextField tbAdress;
    @FXML private PasswordField tbPassword;
    @FXML private PasswordField tbPasswordConfirm;

    public void register(ActionEvent actionEvent) {
        if (notNullOrEmpty(tbName) || notNullOrEmpty(tbMail) || notNullOrEmpty(tbAdress) || notNullOrEmpty(tbPassword)
                || notNullOrEmpty(tbPasswordConfirm)) {
            if(Objects.equals(tbPassword.getText(), tbPasswordConfirm.getText())) {
                Gender gender = Gender.valueOf(genderGroup.getSelectedToggle().getUserData().toString());
                User user = new User(tbName.getText(), tbAdress.getText(), tbMail.getText(), tbPassword.getText(), gender);
                UITools.UIManager uiManager = new UITools.UIManager();
                uiManager.getContainer().register(user);
                uiManager.loadFXML("menu.fxml", "Menu");
            } else {
                Message.show("Error", "Passwords don't match");
            }
        } else {
            Message.show("Error", "Not all fields were filled in");
        }
    }

    private boolean notNullOrEmpty(TextField field) {
        return field.getText() != null || field.getText() != "";
    }
}
