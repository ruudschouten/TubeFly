package main.ui;

import javafx.animation.*;
import javafx.beans.property.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

public class UITools
{
    /**
     * A method to load FXML files using the Bootstrap3 CSS and the Wicked font.
     * @param resource The file name that needs to be loaded
     * @param windowName The name the window should have
     * @return The stage with the scene of the fxml file.
     */
    public Stage loadFXMl(String resource, String windowName){
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle(windowName);
        return primaryStage;
    }
    public static class UIManager{
        /**
         * The current window
         */
        private static Stage stage = null;
        /**
         * The current root Pane that is on screen
         */
        private static Pane currentRoot = null;
        /**
         * The FXMLLoader used to load all FXML Files. This is also used to get the current controller.
         */
        private FXMLLoader loader = null;
        /**
         * Loads the next FXML into the current BorderPane.
         * @param resource The FXML FileName that needs to be loaded
         * @param windowName The name the window should be given, if left empty it will be "Drawingo".
         */
        public void loadFXML(String resource, String windowName){
            loader = new FXMLLoader(getClass().getResource(resource));
            Pane root  = null;
            try{
                root = loader.load();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            Font.loadFont(getClass().getResourceAsStream("Wicked.otf"), 10);
            root.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
            currentRoot = root;
            Scene scene = new Scene(root);
            if(stage == null){
                stage = new Stage();
                stage.setTitle("Drawingo");
                stage.setScene(scene);
                stage.show();
                stage.setResizable(false);
            }
            stage.setScene(scene);
            if(windowName != "") stage.setTitle(windowName);
        }

        /**
         * Loads a Node into the center of a BorderPane.
         * @param resource The filename of the Node wanting to be loaded in the center
         * @throws IOException When the file from the resource is not found
         */
        public void loadCenter(String resource) throws IOException {
            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("resource"));
            Node node = loader2.load();
            if(currentRoot.getClass() == new BorderPane().getClass()){
                ((BorderPane) currentRoot).setCenter(node);
            }
        }

        /**
         * Returns the current controller from the FXMLLoader.
         * @return the Controller as an Object. Needs to be cast to the right class.
         */
        public Object getController(){
            return loader.getController();
        }

        /**
         * Get the root of the current window
         * @return the root node from the current stage. Can return NULL when no fxml file was loaded
         */
        public Node getRoot(){
            return currentRoot;
        }

        /**
         * Gets the current stage
         * @return the current stage, can be NULL when there is no FXML file loaded
         */
        public Stage getStage(){
            return stage;
        }

        /**
         * Returns the current namespace of the FXML Loader
         * @return a Map of String and Object, String being the fx:id and Object being the Node.
         * Can throw a NullPointerException when there is no FXML File
         */
        public Map<String, Object> getNamespace() throws NullPointerException{
            return loader.getNamespace();
        }
        /**
         * Closes the current window.
         */
        public void closeStage(){
            stage.close();
        }
    }
    public static class Animations {
        /**
         *
         * @param node The JavaFX item that will undergo the animation
         * @param fromX The origin X (There will not be an animation going to this)
         * @param fromY The origin Y (There will not be an animation going to this)
         * @param toX The destination X
         * @param toY The destination Y
         * @param time Time in seconds the animation should take.
         * @return a JavaFX animation ready to be played.
         */
        public Animation translation(javafx.scene.Node node, double fromX,double fromY, double toX, double toY, double time){
            node.setTranslateX(fromX);
            node.setTranslateY(fromY);
            TranslateTransition transition = new TranslateTransition();
            transition.setDuration(javafx.util.Duration.seconds(time));
            transition.setNode(node);
            transition.setToX(toX);
            transition.setToY(toY);
            return transition;
        }

        /**
         * Increase the height of a noe to a specific amount.
         * @param node The node that has the height change.
         * @param height The height that the value will be changed to from the current value.
         * @return An JavaFX Animation, not automatically played.
         */
        public Animation increaseHeight(Rectangle node, double height){
            Timeline timeline = new Timeline();
            timeline.setCycleCount(1);
            KeyValue value = new KeyValue(node.heightProperty(), height);
            KeyFrame kf = new KeyFrame(javafx.util.Duration.seconds(1),value);
            timeline.getKeyFrames().add(kf);
            return timeline;
        }

        /**
         * Increase a labels text to be the number.
         * @param label The label that needs the text changed.
         * @param original The original value to be changed from
         * @param destination The target value that it will become
         * @return A JavaFX Animation that does not automatically play
         */
        public Animation increaseLabelNumber(Text label, int original, int destination){
            StringProperty stringProperty = new SimpleStringProperty();
            Timeline timeline = new Timeline();
            label.textProperty().bind(stringProperty);
            final int[] count = {1};
            KeyFrame keyFrame = new KeyFrame(javafx.util.Duration.millis(0), event -> {
                int newvalue = original + count[0];
                stringProperty.setValue(String.valueOf(newvalue));
                count[0]++;
            });
            timeline.getKeyFrames().add(keyFrame);
            timeline.getKeyFrames().add(new KeyFrame(javafx.util.Duration.millis(10)));
            timeline.setCycleCount(destination-original);
            return timeline;

        }

        /**
         * Increases the labels text size to be an amount
         * @param label The label wanting an text increase
         * @param size the size it should increase to
         * @return A JavaFX Animation that does not automatically play.
         */
        public Animation increaseLabelSize(Text label, double size){
            Font font = label.getFont();
            ObjectProperty<Font> fontObjectProperty = label.fontProperty();
            double growth = size - font.getSize();
            growth = growth / 60;
            Timeline timeline = new Timeline();
            double finalGrowth = growth;
            KeyFrame keyFrame = new KeyFrame(javafx.util.Duration.millis(0), event ->
                    fontObjectProperty.setValue(new Font(font.getName(), fontObjectProperty.get().getSize() + finalGrowth))
            );
            timeline.getKeyFrames().add(keyFrame);
            timeline.getKeyFrames().add(new KeyFrame(javafx.util.Duration.millis(10)));
            timeline.setCycleCount(60);
            return timeline;
        }

        /**
         * An animation to automatically make a node pop in to the screen.
         * @param button the node needing to be changed.
         * @param width the final width it should be.
         * @param height the final height it should be.
         * @return A JavaFX Animation that does not automatically play.
         */
        public Animation popinButton(Button button, double width, double height){
            button.setPrefHeight(0.01);
            button.setPrefWidth(0.01);
            DoubleProperty widhtProperty = button.prefWidthProperty();
            DoubleProperty heightProperty = button.prefHeightProperty();
            return popin(width, height, widhtProperty, heightProperty);
        }
        private Timeline popin(double width, double height, DoubleProperty widthProperty, DoubleProperty heightProperty){
            Timeline timeline = new Timeline();
            double widthgrowth = width / 600;
            double heightgrowth = height / 600;
            KeyFrame keyFrame = new KeyFrame(javafx.util.Duration.millis(0), event ->{
                widthProperty.setValue(widthProperty.get() + widthgrowth);
                heightProperty.setValue(heightProperty.get() + heightgrowth);
            });
            timeline.getKeyFrames().add(keyFrame);
            timeline.getKeyFrames().add(new KeyFrame(javafx.util.Duration.millis(200)));
            timeline.setCycleCount(600);
            return timeline;
        }
    }
}