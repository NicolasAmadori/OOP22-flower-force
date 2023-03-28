package flowerforce.view.game;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HowToPlaySceneController {

    @FXML
    private Button btnMenu;
    private final FlowerForceApplication application;

    /**
     * Creates a new controller for a How To Play scene.
     * @param application the application to communicate scene changes
     */
    public HowToPlaySceneController(final FlowerForceApplication application) {
        this.application = application;
    }

    @FXML
    void returnToMenu(ActionEvent event) {
        this.application.menu();
    }
}
