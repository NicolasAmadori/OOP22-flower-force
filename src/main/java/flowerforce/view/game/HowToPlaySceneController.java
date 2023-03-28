package flowerforce.view.game;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class HowToPlaySceneController {
    private final FlowerForceApplication application;

    /**
     * Creates a new controller for a How To Play scene.
     * @param application the application to communicate scene changes
     */
    public HowToPlaySceneController(final FlowerForceApplication application) {
        this.application = application;
    }

    @FXML
    void returnToMenu(MouseEvent event) {
        this.application.menu();
    }
}
