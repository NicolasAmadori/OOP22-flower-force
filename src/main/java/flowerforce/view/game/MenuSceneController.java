package flowerforce.view.game;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
/**
 * Manages JavaFX Menu scene.
 */
public class MenuSceneController {

    @FXML
    private Text scoreText;

    private final FlowerForceApplication application;

    /**
     * Creates a new controller for a Menu scene.
     * @param application the application to communicate scene changes
     */
    public MenuSceneController(final FlowerForceApplication application) {
        this.application = application;
    }

    /**
     * Sets Player's score in the Menu.
     */
    public void initialize() {
        final int coins = this.application.getController().getPlayerScoreRecord();
        this.scoreText.setText(this.scoreText.getText().concat(Integer.toString(coins)));
    }

    /**
     * Starts a new level.
     * @param event the MouseEvent to handle
     */
    @FXML
    void start(final MouseEvent event) {
        final int levelId = Integer.parseInt(((ImageView) event.getSource()).getAccessibleText());
        if (levelId <= this.application.getController().getLastUnlockedLevelId()) {
            this.application.game(levelId);
        } else  {
            final Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Level not unlocked");
            alert.setHeaderText("Level " + levelId + " is not unlocked");
            alert.setContentText("This level has not been unlocked yet, right now you can play up to Level "
                                  + this.application.getController().getLastUnlockedLevelId() + ".");
            alert.showAndWait();
        }
    }
}
