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
        final int score = this.application.getController().getPlayerScoreRecord();
        this.scoreText.setText(this.scoreText.getText().concat(Integer.toString(score)));
    }

    /**
     * Starts a new level.
     * @param event the MouseEvent to handle
     */
    @FXML
    void startLevel(final MouseEvent event) {
        final int levelId = Integer.parseInt(((ImageView) event.getSource()).getAccessibleText());
        if (levelId <= this.application.getController().getLastUnlockedLevelId()) {
            this.application.levelGame(levelId);
        } else  {
            final Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Level not unlocked");
            alert.setHeaderText("Level " + levelId + " is not unlocked");
            alert.setContentText("This level has not been unlocked yet, right now you can play up to Level "
                                  + this.application.getController().getLastUnlockedLevelId() + ".");
            alert.showAndWait();
        }
    }

    /**
     * Starts an Adventure mode game.
     * @param event the MouseEvent to handle
     */
    @FXML
    void startAdventureGame(final MouseEvent event) {
        this.application.adventureGame();
    }

    /**
     * Opens How to play tutorial.
     * @param event the MouseEvent to handle
     */
    @FXML
    void howToPlay(final MouseEvent event) {
        this.application.howToPlay();
    }

    /**
     * Opens the shop.
     * @param event the MouseEvent to handle
     */
    @FXML
    void shop(final MouseEvent event) {
        this.application.shop();
    }
}
