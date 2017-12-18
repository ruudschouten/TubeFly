package main.ui.client;

import javafx.application.Application;
import javafx.stage.Stage;
import main.rmi.ClientContainer;
import main.ui.UITools;
import main.ui.client.controllers.LoginController;

public class TubeClient extends Application {
    ClientContainer container;

    @Override
    public void start(Stage primaryStage) throws Exception {
        UITools.UIManager uiManager = new UITools.UIManager();
        uiManager.loadFXML("/main/resources/login.fxml", "Login");
        LoginController loginController = (LoginController) uiManager.getController();
    }
}
