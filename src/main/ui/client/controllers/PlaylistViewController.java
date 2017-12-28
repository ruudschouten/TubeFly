package ui.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import play.Playlist;
import play.Song;
import play.User;
import rmi.ClientContainer;
import ui.Message;
import ui.ResourceHandler;
import ui.UITools;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

public class PlaylistViewController {
    @FXML
    private Button btnFollow;
    @FXML
    private VBox songContainer;
    @FXML
    private Label playlistName;
    @FXML
    private TextField tbSearch;
    @FXML
    private Label currentSongInfo;
    @FXML
    private ProgressBar songTimeProgress;

    private ClientContainer container;
    private UITools.UIManager uiManager;

    private Playlist playlist;

    public void initialize() {
        uiManager = new UITools.UIManager();
        container = ResourceHandler.getContainer();
        playlist = container.getSelectedPlaylist();
        playlistName.setText(playlist.getName());
        //Admin
        if(container.getUser() == null) {
            btnFollow.setVisible(false);
        }
        displayPlaylists();
    }

    private void displayPlaylists() {
        for (Song s : playlist.getSongs()) {
            addSongUI(s);
        }
    }

    private void addSongUI(Song s) {
        HBox root = new HBox();
        root.getStyleClass().add("playlist");
        AnchorPane pane = new AnchorPane();
        Label songInfo = new Label(String.format("%s - %s", s.getName(), s.getArtist()));
        Label songTime = new Label(String.format("%s", s.getLength()));
        AnchorPane.setBottomAnchor(songInfo, 5.0);
        AnchorPane.setLeftAnchor(songInfo, 35.0);
        AnchorPane.setTopAnchor(songInfo, 5.0);
        AnchorPane.setBottomAnchor(songTime, 5.0);
        AnchorPane.setRightAnchor(songTime, 0.0);
        AnchorPane.setTopAnchor(songTime, 5.0);
        Button playBtn = new Button();
        playBtn.setOnAction(this::songPlayPause);
        playBtn.getStyleClass().add("musicbutton");
        playBtn.setText(" ▶ ");
        pane.getChildren().addAll(songInfo, songTime, playBtn);
        root.getChildren().add(pane);
        HBox.setHgrow(pane, Priority.ALWAYS);
        VBox.setMargin(root, new Insets(10, 0, 10, 0));
        songContainer.getChildren().add(root);
    }

    public void filterSongs(ActionEvent actionEvent) {
        songContainer.getChildren().clear();
        String searchCriteria = tbSearch.getText();
        List<Song> songs = playlist.getSongs(searchCriteria);
        for (Song s : songs) {
            addSongUI(s);
        }
    }

    public void songPlayPause(ActionEvent actionEvent) {
    }

    public void previousSong(ActionEvent actionEvent) {
    }

    public void currentSongPlayPause(ActionEvent actionEvent) {
    }

    public void nextSong(ActionEvent actionEvent) {
    }

    public void toggleShuffle(ActionEvent actionEvent) {
    }

    public void toggleLoop(ActionEvent actionEvent) {
    }

    public void toMenu(ActionEvent actionEvent) {
        new UITools.UIManager().loadFXML("menu.fxml", "Menu");
    }

    public void toggleFollow(ActionEvent actionEvent) {
        if (Objects.equals(btnFollow.getText(), "Follow")) {
            if (container.follow(playlist)) {
                Message.show("Success", String.format("Successfully followed %s", playlist.getName()));
                btnFollow.setText("Unfollow");
            }
        } else {
            if (container.unfollow(playlist)) {
                Message.show("Success", String.format("Successfully unfollowed %s", playlist.getName()));
                btnFollow.setText("Unfollow");
            }
        }
    }
}
