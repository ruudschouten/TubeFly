package main.ui.client;

import javafx.application.Application;
import javafx.stage.Stage;
import main.rmi.ClientContainer;
import main.ui.UITools;

public class TubeClient extends Application {
    public ClientContainer container;

    @Override
    public void start(Stage primaryStage) throws Exception {
        UITools uiTools = new UITools();
        Stage stage = uiTools.loadFXMl("/main/resources/login.fxml", "Login");
        stage.show();
    }
}
