package ui.client;

import javafx.application.Application;
import javafx.stage.Stage;
import rmi.ClientContainer;
import ui.UITools;

public class TubeClient extends Application {
    ClientContainer container;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.close();
//        container = new ClientContainer();
        UITools.UIManager uiManager = new UITools.UIManager();
        uiManager.setContainer(container);
        uiManager.loadFXML("login.fxml", "Login");
    }
}
