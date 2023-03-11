package flowerforce.view.game;

import flowerforce.controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
/**
 * Manages JavaFX Menu scene.
 */
public class MenuSceneController {

    private final FlowerForceApplication application;
    private final Controller mainController;

    /**
     * Creates a new controller for a Menu scene.
     * @param application the application to communicate scene changes
     * @param mainController the controller to communicate events to
     */
    public MenuSceneController(final FlowerForceApplication application, final Controller mainController) {
        this.application = application;
        this.mainController = mainController;
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
            this.mainController.StartNewLevelGame(levelId);
            this.application.game();
        } catch (NumberFormatException e) {
            mainController.startNewInfiniteGame();
            this.application.game();
        }
    }
}
