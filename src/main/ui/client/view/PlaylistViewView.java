package ui.client.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import play.User;
import ui.client.controllers.PlaylistViewController;

import java.io.IOException;

public class PlaylistViewView {

    public void start(Stage primaryStage, User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/playlistview.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Playlist View");
        PlaylistViewController controller = loader.getController();
        controller.setStage(primaryStage);
        controller.setUser(user);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
