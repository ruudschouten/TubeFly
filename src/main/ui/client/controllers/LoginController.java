package ui.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import database.data.Database;
import database.logic.UserContext;
import database.repositories.UserRepository;
import log.Logger;
import play.User;
import ui.Message;
import ui.UITools;

import java.sql.SQLException;
import java.util.logging.Level;

public class LoginController {
    @FXML private TextArea taLog;
    @FXML private TextField tbMail;
    @FXML private PasswordField tbPassword;

    private Stage stage;
    private Logger logger;

    public void initialize() {
        logger = new Logger("LoginController", Level.SEVERE, Level.SEVERE);
        if(Database.getCon() != null) {
            taLog.setText(taLog.getText() + "[INFO] Server is online\n");
        } else {
            taLog.setText(taLog.getText() + "[ERROR] Server is offline\n");
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void showMain(ActionEvent actionEvent) {
        UserRepository userRepository = new UserRepository(new UserContext());
        String mail = tbMail.getText();
        String pass = tbPassword.getText();
        try {
            User user = userRepository.login(mail, pass);
            if(user != null) {
                UITools.UIManager uiManager = new UITools.UIManager();
                uiManager.loadFXML("/main/resources/playlistcollection.fxml", "Playlists");
            } else {
                Message.Show("Invalid login", "Username and password did not match\nPlease try again.");
                logger.log(Level.WARNING, "Username and password did not match");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.toString());
        }
    }

    public void showRegUser(MouseEvent mouseEvent) {
        UITools.UIManager uiManager = new UITools.UIManager();
        uiManager.loadFXML("/main/resources/register.fxml", "Playlists");
    }
}
