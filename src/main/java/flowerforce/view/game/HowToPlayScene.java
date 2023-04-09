package flowerforce.view.game;

/**
 * Models an HowToPlay scene.
 */
public class HowToPlayScene extends AbstractFlowerForceScene {
    private static final String FXML_FILE_NAME = "HowToPlay.fxml";

    /**
     * Create a How To Play View.
     * @param application The application that displays the scene
     */
    protected HowToPlayScene(final FlowerForceApplication application) {
        super(FXML_FILE_NAME, new HowToPlaySceneController(application));
    }
}
