package ui.client.controllers;

import javafx.application.Platform;
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
import play.Playlist;
import play.Song;
import rmi.ClientContainer;
import ui.Message;
import ui.ResourceHandler;
import ui.SongButton;
import ui.UITools;

import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class PlaylistViewController implements IController {
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

    private Timer timer;
    private Playlist playlist;
    private Song currentSong;

    private boolean isPaused;

    public void initialize() {
        uiManager = new UITools.UIManager();
        container = ResourceHandler.getContainer();
        container.setController(this);
        playlist = container.getSelectedPlaylist();
        playlistName.setText(playlist.getName());
        //Admin
        if (container.getUser() == null) {
            btnFollow.setVisible(false);
        }
        displaySongs();
    }

    private void displaySongs() {
        songContainer.getChildren().clear();
        for (Song s : playlist.getSongs()) {
            addSongUI(s);
        }
    }

    private void startSong(Song s) {
        timer.cancel();
        songTimeProgress.setProgress(0);
        currentSongInfo.setText(s.getName());
        setupTimer(s);
    }

    private void setupTimer(Song s) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                songTimeProgress.setProgress(s.getCurrentTime().getMillis() / 100);
            }
        }, 0, 1000);
    }

    private void addSongUI(Song s) {
        HBox root = new HBox();
        root.getStyleClass().add("playlist");
        AnchorPane pane = new AnchorPane();
        Label songInfo = new Label(String.format("%s - %s", s.getName(), s.getArtist()));
        Label songTime = new Label(String.format("%s", s.getLengthString()));
        AnchorPane.setBottomAnchor(songInfo, 5.0);
        AnchorPane.setLeftAnchor(songInfo, 35.0);
        AnchorPane.setTopAnchor(songInfo, 5.0);
        AnchorPane.setBottomAnchor(songTime, 5.0);
        AnchorPane.setRightAnchor(songTime, 0.0);
        AnchorPane.setTopAnchor(songTime, 5.0);
        SongButton playBtn = new SongButton(s);
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
        SongButton btn = (SongButton) actionEvent.getSource();
        Song newSong = btn.getSong();
        if (currentSong == null || !currentSong.equals(newSong)) {
            currentSong = newSong;
            startSong(currentSong);
            isPaused = false;
        } else {
            if (isPaused) {
                btn.setText(" ▶ ");
                isPaused = false;
            } else {
                btn.setText(" \u23F8 ");
                isPaused = true;
            }
        }
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
        uiManager.loadFXML("menu.fxml", "Menu");
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
                btnFollow.setText("Follow");
            }
        }
    }

    @Override
    public void update() {
        playlist = container.getSelectedPlaylist();
        playlistName.setText(playlist.getName());
        Platform.runLater(this::displaySongs);
    }

    public void showAddSong(ActionEvent actionEvent) {
        uiManager.openInNewWindow("newsong.fxml", "Add Song");
    }
}
