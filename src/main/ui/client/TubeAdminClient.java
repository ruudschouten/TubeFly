package ui.client;

import javafx.application.Application;
import javafx.stage.Stage;
import ui.UITools;

public class TubeAdminClient extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        new RmiClient("TubeAdminClient");
        primaryStage.close();
        new UITools.UIManager().loadFXML("menu.fxml", "Menu");
    }
}
