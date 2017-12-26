package ui.client;

import javafx.application.Application;
import javafx.stage.Stage;
import ui.UITools;

public class TubeClient extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        new RmiClient("TubeClient");
        primaryStage.close();
        new UITools.UIManager().loadFXML("login.fxml", "Login");
    }
}
