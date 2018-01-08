package ui.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import database.data.Database;
import play.User;
import rmi.ClientContainer;
import security.Hash;
import ui.Message;
import ui.ResourceHandler;
import ui.UITools;

import java.util.logging.Level;

public class LoginController {
    @FXML private TextArea taLog;
    @FXML private TextField tbMail;
    @FXML private PasswordField tbPassword;

    private UITools.UIManager uiManager;

    public void initialize() {
        uiManager = new UITools.UIManager();
        if (Database.getCon() != null) {
            taLog.setText(taLog.getText() + "[INFO] Server is online\n");
        } else {
            taLog.setText(taLog.getText() + "[ERROR] Server is offline\n");
        }

        uiManager = new UITools.UIManager();
    }

    public void showMain(ActionEvent actionEvent) {
        String mail = tbMail.getText();
        String pass = tbPassword.getText();
        ClientContainer container = ResourceHandler.getContainer();
        User user = container.login(mail, Hash.SHA256(pass));
        if (user != null) {
            uiManager.loadFXML("menu.fxml", "Menu");
        } else {
            Message.show("Invalid login", "Username and password did not match\nPlease try again.");
            ResourceHandler.getLogger().log(Level.WARNING, "Username and password did not match");
        }
    }

    public void showRegUser(MouseEvent mouseEvent) {
        uiManager.loadFXML("register.fxml", "Register");
    }
}
