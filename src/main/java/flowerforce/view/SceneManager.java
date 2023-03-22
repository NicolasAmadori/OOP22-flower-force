package flowerforce.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Manages JavaFX scenes in view.
 */
public class SceneManager {

    /**
     * Starts a new level.
     * @param event the MouseEvent to handle
     */
    @FXML
    void start(final MouseEvent event) throws IOException {
        final FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource("flowerforce/game/Garden.fxml"));
        final Parent root = loader.load();
        final Scene scene = new Scene(root);
        final Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
