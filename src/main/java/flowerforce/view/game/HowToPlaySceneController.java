package flowerforce.view.game;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "I need the exact application instance"
    )
    public HowToPlaySceneController(final FlowerForceApplication application) {
        this.application = application;
    }

    @FXML
    void returnToMenu(final MouseEvent event) {
        this.application.menu();
    }
}
