package ui.client.controllers;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
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

import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;

import java.util.List;
import java.util.Objects;

public class PlaylistViewController implements IController {
    @FXML private Slider volumeSlider;
    @FXML private Button btnPlayer;
    @FXML private Button btnAdd;
    @FXML private Button btnFollow;
    @FXML private VBox songContainer;
    @FXML private Label playlistName;
    @FXML private TextField tbSearch;
    @FXML private Label currentSongInfo;
    @FXML private ProgressBar songTimeProgress;

    private ClientContainer container;
    private UITools.UIManager uiManager;

    private Song currentSong;

    private Playlist playlist;
    private MediaPlayer player;

    public void initialize() {
        uiManager = new UITools.UIManager();
        container = ResourceHandler.getContainer();
        container.setController(this);
        playlist = container.getSelectedPlaylist();
        playlistName.setText(playlist.getName());

        //Follow button
        if(playlist.getFollowers().contains(container.getUser())) {
            btnFollow.setText("Unfollow");
        }

        if(!playlist.getCreator().equals(container.getUser())) {
            btnAdd.setVisible(false);
        }
        //Admin
        if (container.getUser() == null) {
            btnFollow.setVisible(false);
            btnAdd.setVisible(true);
        }

        displaySongs();
        (new NativeDiscovery()).discover();
    }

    private void displaySongs() {
        songContainer.getChildren().clear();
        for (Song s : playlist.getSongs()) {
            addSongUI(s);
        }
    }

    private void startSong(Song s) {
        if(player == null) setupVLC();
        currentSong = s;
        player.prepareMedia(s.getURL());
        player.play();
    }

    private void setupVLC() {
        String[] args = {
                "--vout", "dummy",          // we don't want video (output)
                };
        MediaPlayerFactory factory = new MediaPlayerFactory(args);
        player = factory.newEmbeddedMediaPlayer();
        player.setPlaySubItems(true); // <--- This is very important for YouTube media
        player.setVolume(50);

        player.addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void buffering(MediaPlayer mediaPlayer, float newCache) {
                Platform.runLater(() -> currentSongInfo.setText("Buffering " + newCache + "\n" + currentSong.getName() + " - " + currentSong.getArtist()));
                songTimeProgress.setProgress(newCache);
            }

            @Override
            public void mediaSubItemAdded(MediaPlayer mediaPlayer, libvlc_media_t subItem) {
                //This is empty because it works when it's empty
                }
        });
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> player.setVolume(newValue.intValue()));
    }

    private void addSongUI(Song s) {
        HBox root = new HBox();
        root.getStyleClass().add("playlist");
        AnchorPane pane = new AnchorPane();
        Label songInfo = new Label(String.format("%s - %s", s.getName(), s.getArtist()));
        AnchorPane.setBottomAnchor(songInfo, 5.0);
        AnchorPane.setLeftAnchor(songInfo, 35.0);
        AnchorPane.setTopAnchor(songInfo, 5.0);
        SongButton playBtn = new SongButton(s);
        playBtn.setOnAction(this::songPlayPause);
        playBtn.getStyleClass().add("musicbutton");
        playBtn.setText(ResourceHandler.PLAY_CHAR);
        pane.getChildren().addAll(songInfo, playBtn);
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
        startSong(btn.getSong());
        btnPlayer.setText(ResourceHandler.PAUSE_CHAR);
    }

    public void currentSongPlayPause(ActionEvent actionEvent) {
        if(player == null) {
            startSong(playlist.getSongs().get(0));
            btnPlayer.setText(ResourceHandler.PAUSE_CHAR);
        }
        if(player.isPlaying()) {
            player.pause();
            btnPlayer.setText(ResourceHandler.PLAY_CHAR);
        }
        else if(!player.isPlaying()) {
            player.play();
            btnPlayer.setText(ResourceHandler.PAUSE_CHAR);
        }
    }

    public void toMenu(ActionEvent actionEvent) {
        if(player != null) player.stop();
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

    public void showAddSong(ActionEvent actionEvent) {
        uiManager.openInNewWindow("newsong.fxml", "Add Song");
    }

    @Override
    public void update() {
        playlist = container.getSelectedPlaylist();
        playlistName.setText(playlist.getName());
        Platform.runLater(this::displaySongs);
    }
}
