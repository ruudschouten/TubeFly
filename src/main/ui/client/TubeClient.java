package ui.client;

import javafx.application.Application;
import javafx.stage.Stage;
import rmi.ClientContainer;
import ui.UITools;
import ui.client.controllers.LoginController;

public class TubeClient extends Application {
    ClientContainer container;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.close();
        UITools.UIManager uiManager = new UITools.UIManager();
        uiManager.loadFXML("/main/resources/login.fxml", "Login");
        LoginController loginController = (LoginController) uiManager.getController();
    }
}
