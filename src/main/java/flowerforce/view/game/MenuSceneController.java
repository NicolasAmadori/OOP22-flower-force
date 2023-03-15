package flowerforce.view.game;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
/**
 * Manages JavaFX Menu scene.
 */
public class MenuSceneController {

    private final FlowerForceApplication application;

    /**
     * Creates a new controller for a Menu scene.
     * @param application the application to communicate scene changes
     * @param mainController the controller to communicate events to
     */
    public MenuSceneController(final FlowerForceApplication application) {
        this.application = application;
    }

    /**
     * Starts a new level.
     * @param event the MouseEvent to handle
     */
    @FXML
    void start(final MouseEvent event) {
        final int levelId = Integer.parseInt(((ImageView) event.getSource()).getAccessibleText());
        this.application.game(levelId);
    }
}
