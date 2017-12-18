package main.ui.client.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.play.User;
import main.ui.client.controllers.*;

import java.io.IOException;

public class RegisterView {

    public void start(Stage primaryStage, User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/register.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Register");
        MenuController controller = loader.getController();
        controller.setStage(primaryStage);
        controller.setUser(user);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}