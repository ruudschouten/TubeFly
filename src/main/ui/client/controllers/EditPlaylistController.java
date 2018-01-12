package ui.client.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
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

public class EditPlaylistController implements IController {
    @FXML private VBox songContainer;
    @FXML private TextField tbDescription;
    @FXML private TextField tbName;
    @FXML private TextField tbSearch;
    @FXML private TextField tbSong;

    private Playlist playlist;

    private ClientContainer container;
    private UITools.UIManager uiManager;

    public void initialize() {
        uiManager = new UITools.UIManager();
        container = ResourceHandler.getContainer();
        if(container.getSelectedPlaylist() == null) {
            playlist = new Playlist("", container.getUser());
            container.uploadPlaylist(playlist);
            container.setSelectedPlaylist(playlist);
        } else {
            playlist = container.getSelectedPlaylist();
            tbName.setText(playlist.getName());
            tbDescription.setText(playlist.getDescription());
            for (Song s : playlist.getSongs()) {
                addSongUI(s);
            }
        }
        container.setController(this);
    }

    public void addSong(ActionEvent actionEvent) {
        uiManager.openInNewWindow("newsong.fxml", "Add Song");
    }

    public void savePlaylist(ActionEvent actionEvent) {
        String name = tbName.getText();
        String description = tbDescription.getText();
        if(container.updatePlaylist(name, description)) {
            uiManager.loadFXML("menu.fxml", "Menu");
        }
        else {
            Message.show("Error", "Please enter a name");
        }
    }

    public void backToMenu(ActionEvent actionEvent) {
        uiManager.loadFXML("menu.fxml", "Menu");
    }

    public void removeSong(ActionEvent actionEvent) {
        SongButton btn = (SongButton) actionEvent.getSource();
        Song song = btn.getSong();
        container.removeSong(playlist, song);
    }

    private void addSongUI(Song s) {
        HBox root = new HBox();
        root.getStyleClass().add("playlist");
        AnchorPane pane = new AnchorPane();
        Label songInfo = new Label(String.format("%s %s", s.getName(), s.getArtist()));
        SongButton btn = new SongButton(s);
        btn.getStyleClass().addAll("deletebutton");
        btn.setText("x");
        btn.setOnAction(this::removeSong);
        AnchorPane.setBottomAnchor(songInfo, 5.0);
        AnchorPane.setTopAnchor(songInfo, 5.0);
        AnchorPane.setLeftAnchor(songInfo, 5.0);
        AnchorPane.setRightAnchor(btn, 0.0);
        pane.getChildren().addAll(songInfo, btn);
        root.getChildren().add(pane);
        HBox.setHgrow(pane, Priority.ALWAYS);
        VBox.setMargin(root, new Insets(10, 0, 10, 0));
        songContainer.getChildren().add(root);
    }

    private void displaySongs() {
        songContainer.getChildren().clear();
        for (Song s : playlist.getSongs()) {
            addSongUI(s);
        }
    }

    @Override
    public void update() {
        playlist = container.getSelectedPlaylist();
        Platform.runLater(this::displaySongs);
    }
}
