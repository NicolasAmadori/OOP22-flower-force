package flowerforce.view.game;

import flowerforce.controller.GameController;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
/**
 * Manages JavaFX Menu scene.
 */
public class MenuSceneController {

    private final FlowerForceApplication application;
    private final GameController gameController;

    /**
     * Creates a new controller for a Menu scene.
     * @param application the application to communicate scene changes
     * @param gameController the controller to communicate events to
     */
    public MenuSceneController(final FlowerForceApplication application, final GameController gameController) {
        this.application = application;
        this.gameController = gameController;
    }

    /**
     * Starts a new level.
     * @param event the MouseEvent to handle
     */
    @FXML
    void start(final MouseEvent event) {
        try {
            final String buttonPressed = ((ImageView) event.getSource()).getId();
            final int levelId = Integer.parseInt(buttonPressed.substring(buttonPressed.length() - 1));
            this.gameController.startLevelGame(levelId);
        } catch (NumberFormatException e) {
            gameController.startInfiniteGame();
        }
        this.application.game();
    }
}
