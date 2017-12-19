package ui.client.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import play.User;
import ui.client.controllers.*;

import java.io.IOException;

public class MenuView {

    public void start(Stage primaryStage, User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/menu.fxml"));
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