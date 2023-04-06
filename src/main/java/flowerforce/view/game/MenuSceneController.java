package flowerforce.view.game;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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
    private Text scoreRecord;

    private final FlowerForceApplication application;

    /**
     * Creates a new controller for a Menu scene.
     * @param application the application to communicate scene changes
     */
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP2",
        justification = "application is stored to call its methods and obtain controller"
    )
    public MenuSceneController(final FlowerForceApplication application) {
        this.application = application;
    }

    /**
     * Sets Player's score in the Menu.
     */
    public void initialize() {
        final int record = this.application.getController().getPlayerScoreRecord();
        this.scoreRecord.setText(this.scoreRecord.getText().concat(Integer.toString(record)));
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
