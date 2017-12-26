package ui.client.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import play.Gender;
import play.User;
import ui.Message;
import ui.ResourceHandler;
import ui.UITools;

import java.util.Objects;

public class RegisterController {
    @FXML private RadioButton rbMale;
    @FXML private TextField tbName;
    @FXML private ToggleGroup genderGroup;
    @FXML private TextField tbMail;
    @FXML private TextField tbAdress;
    @FXML private PasswordField tbPassword;
    @FXML private PasswordField tbPasswordConfirm;

    private Gender gender;

    public void initialize() {
        genderGroup.selectToggle(rbMale);
        gender = Gender.MALE;
        genderGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                RadioButton rb = (RadioButton)newValue;
                gender = Gender.valueOf(rb.getText().toUpperCase());
            }
        });
    }

    public void register(ActionEvent actionEvent) {
        if (notNullOrEmpty(tbName) || notNullOrEmpty(tbMail) || notNullOrEmpty(tbAdress) || notNullOrEmpty(tbPassword)
                || notNullOrEmpty(tbPasswordConfirm)) {
            if (Objects.equals(tbPassword.getText(), tbPasswordConfirm.getText())) {
                User user = new User(tbName.getText(), tbAdress.getText(), tbMail.getText(), tbPassword.getText(), gender);
                if (ResourceHandler.getContainer().register(user) != null) {
                    UITools.UIManager uiManager = new UITools.UIManager();
                    uiManager.loadFXML("menu.fxml", "Menu");
                } else {
                    Message.show("Error", "Unable to register account");
                }
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
