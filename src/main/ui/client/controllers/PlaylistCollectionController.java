package ui.client.controllers;

import javafx.event.ActionEvent;
import ui.UITools;

public class PlaylistCollectionController {
    private UITools.UIManager uiManager;

    public void initialize() {
        uiManager = new UITools.UIManager();
    }

    public void backToMenu(ActionEvent actionEvent) {
        uiManager.loadFXML("menu.fxml", "Menu");
    }
}
