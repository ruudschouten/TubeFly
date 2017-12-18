package main.ui.client;

import javafx.application.Application;
import javafx.stage.Stage;
import main.rmi.ClientContainer;
import main.ui.UITools;
import main.ui.client.controllers.loginController;

public class TubeClient extends Application {
    public ClientContainer container;

    @Override
    public void start(Stage primaryStage) throws Exception {
        UITools.UIManager uiManager = new UITools.UIManager();
        uiManager.loadFXML("/main/resources/login.fxml", "Login");
        loginController loginController = (main.ui.client.controllers.loginController) uiManager.getController();
    }
}
