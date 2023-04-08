package flowerforce.view.game;

/**
 * Models the scene of the game, extends {@link AbstractFlowerForceScene}.
 */
public class GameScene extends AbstractFlowerForceScene {

    private static final String FXML_FILE_NAME = "Game.fxml";
    private static final String IMAGE_NAME = "Garden.png";

    /**
     * Creates a new Game view.
     * @param application which sets the scene
     */
    public GameScene(final FlowerForceApplication application) {
        super(FXML_FILE_NAME, IMAGE_NAME, new GameSceneController(application));
    }
}
