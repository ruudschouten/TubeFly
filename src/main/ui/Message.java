package ui;

import javafx.scene.control.Alert;

public class Message {

    private Message() { }

    public static void show(String title, String body) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(title);
        alert.setContentText(body);
        alert.show();
    }
}
