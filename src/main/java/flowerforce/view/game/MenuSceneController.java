package flowerforce.view.game;

import flowerforce.controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
/**
 * Manages JavaFX Menu scene.
 */
public class MenuSceneController {

    private final static int INFINITE_MODE_ID = 0;

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
        final int levelId = Integer.parseInt(((ImageView) event.getSource()).getAccessibleText());
        if (levelId <= this.mainController.getLastUnlockedLevelId() + 1) {
            if (levelId == INFINITE_MODE_ID) {
                this.mainController.startNewInfiniteGame();
            }
            else {
                this.mainController.StartNewLevelGame(levelId);
            }
            this.application.game();
        }
        
    }
}
