package ui.client.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import play.Playlist;
import rmi.ClientContainer;
import ui.ResourceHandler;
import ui.UITools;

import java.util.List;

public class PlaylistCollectionController {
    @FXML
    private VBox playlistContainer;
    private UITools.UIManager uiManager;
    private ClientContainer container;

    private List<Playlist> playlists;

    public void initialize() {
        uiManager = new UITools.UIManager();
        container = ResourceHandler.getContainer();
        playlists = container.getPlaylistsByUser();
        for (Playlist p : playlists) {
            addPlaylistUI(p);
        }
    }

    public void backToMenu(ActionEvent actionEvent) {
        uiManager.loadFXML("menu.fxml", "Menu");
    }

    private void addPlaylistUI(Playlist playlist) {
        String songCountInfo = "";
        int songCount = playlist.getSongs().size();
        if (songCount == 1) {
            songCountInfo = "1 song";
        } else {
            songCountInfo = String.format("%s songs", songCount);
        }

        HBox root = new HBox();
        root.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            container.setSelectedPlaylist(playlist);
            uiManager.loadFXML("editplaylist.fxml", playlist.getName());
        });
        HBox playlistInfo = new HBox();
        root.getStyleClass().add("playlist");
        Label playlistName = new Label(String.format("%s ", playlist.getName()));
        playlistName.setStyle("-fx-font-weight: bold;");
        Label playlistCreatedBy = new Label("Created by: ");
        Label playlistCreator = new Label(String.format("%s ", playlist.getCreator().getName()));
        playlistCreator.setStyle("-fx-font-weight: bold");
        Label playlistSongs = new Label(String.format("- %s", songCountInfo));
        playlistInfo.getChildren().addAll(playlistName, playlistCreatedBy, playlistCreator, playlistSongs);
        root.getChildren().add(playlistInfo);
        HBox.setMargin(playlistInfo, new Insets(0, 0, 0, 6));
        VBox.setMargin(root, new Insets(0, 0, 10, 0));
        playlistContainer.getChildren().add(root);
    }
}
