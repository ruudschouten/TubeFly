package main.ui.client.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.play.User;
import main.ui.client.controllers.playlistCollectionController;

import java.io.IOException;

public class PlaylistCollectionView {
    public void start(Stage primaryStage, User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/playlistcollection.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Playlist Collection");
        playlistCollectionController controller = loader.getController();
        controller.setStage(primaryStage);
        controller.setUser(user);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
