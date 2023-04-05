package flowerforce.view.game;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

/**
 * The controller of the HowToPlayScene.
 */
public final class HowToPlaySceneController {
    private final FlowerForceApplication application;

    /**
     * Creates a new controller for a How To Play scene.
     * @param application the application to communicate scene changes
     */
    public HowToPlaySceneController(final FlowerForceApplication application) {
        this.application = application;
    }

    @FXML
    void returnToMenu(final MouseEvent event) {
        this.application.menu();
    }
}
