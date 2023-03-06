package flowerforce.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MyMenuGUIApp extends Application{
    @Override
    public void start(final Stage primaryStage) throws Exception {
        final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("flowerforce/game/SimpleMenu.fxml"));
        Scene scene = new Scene(root);
        // scene.getStylesheets().add(getClass().getResource("/css/styling.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hello");
        primaryStage.show();
    }
}
