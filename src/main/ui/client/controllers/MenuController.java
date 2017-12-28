package ui.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import play.Playlist;
import rmi.ClientContainer;
import ui.ResourceHandler;
import ui.UITools;

import java.util.List;

public class MenuController {
    @FXML
    private VBox playlistContainer;
    @FXML
    private TextField tbSearch;

    private UITools.UIManager uiManager;
    private ClientContainer container;

    public void initialize() {
        uiManager = new UITools.UIManager();
        container = ResourceHandler.getContainer();
        List<Playlist> playlists = container.getPlaylists(10);
        for (Playlist p : playlists) {
            addPlaylistUI(p);
        }
    }

    public void filterPlaylists(ActionEvent actionEvent) {
    }

    private void addPlaylistUI(Playlist playlist) {
        String songCountInfo = "";
        int songCount = playlist.getSongs().size();
        if (songCount == 1) { songCountInfo = "1 song"; }
        else { songCountInfo = String.format("%s songs", songCount); }

        HBox root = new HBox();
        VBox playlistInfo = new VBox();
        HBox playlistInfoContainer = new HBox();
        root.getStyleClass().add("playlist");
        Label playlistName = new Label(String.format("%s ", playlist.getName()));
        playlistName.setStyle("-fx-font-weight: bold; -fx-font-size: 16");
        Label playlistCreatedBy = new Label("Created by: ");
        Label playlistCreator = new Label(String.format("%s ", playlist.getCreator().getName()));
        playlistCreator.setStyle("-fx-font-weight: bold");
        Label playlistSongs = new Label(String.format("- %s", songCountInfo));
        playlistInfoContainer.getChildren().addAll(playlistCreatedBy, playlistCreator, playlistSongs);
        playlistInfo.getChildren().addAll(playlistName, playlistInfoContainer);
        root.getChildren().add(playlistInfo);
        HBox.setMargin(playlistInfo, new Insets(0, 0,0,6));
        VBox.setMargin(root, new Insets(0, 0, 10, 0));
        playlistContainer.getChildren().add(root);
    }
}
