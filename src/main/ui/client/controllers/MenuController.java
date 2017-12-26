package ui.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import play.Playlist;
import play.User;
import rmi.ClientContainer;
import ui.ResourceHandler;
import ui.UITools;

import java.util.ArrayList;
import java.util.List;

public class MenuController {
    @FXML private VBox playlistContainer;
    @FXML private TextField tbSearch;

    private UITools.UIManager uiManager;

    public void initialize() {
        uiManager = new UITools.UIManager();
        ClientContainer container = ResourceHandler.getContainer();
        List<Playlist> playlists = container.getPlaylists(10);
        //TODO: figure out why this crashes [Can't cast sun ... to Playlist
//        for(Playlist p :  playlists) {
//            System.out.println(p.getName());
//            addPlaylistUI(p);
//        }
    }

    public void filterPlaylists(ActionEvent actionEvent) {
    }

    private void addPlaylistUI(Playlist playlist) {
        /*
        <HBox styleClass="playlist" stylesheets="@userstyle.css">
            <children>
                <Label text="Playlist name - Creator - Song amount"/>
            </children>
            <VBox.margin>
                <Insets bottom="10.0"/>
            </VBox.margin>
        </HBox>
         */
        HBox root = new HBox();
        root.getStylesheets().add("@userstyle.css");
        root.getStyleClass().add("playlist");
        Label playlistInfo = new Label(playlist.getName());
        root.getChildren().add(playlistInfo);
        VBox.setMargin(root, new Insets(0,0,10,0));
        playlistContainer.getChildren().add(root);
    }
}
