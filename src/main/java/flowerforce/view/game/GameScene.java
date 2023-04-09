package flowerforce.view.game;

import flowerforce.common.ResourceFinder;
/**
 * Models the scene of the game, extends {@link AbstractFlowerForceScene}.
 */
public class GameScene extends AbstractFlowerForceScene {

    private static final String FXML_FILE_NAME = "Game.fxml";

    /**
     * Creates a new Game view.
     * @param application which sets the scene
     */
    public GameScene(final FlowerForceApplication application) {
        super(ResourceFinder.getFXMLPath(FXML_FILE_NAME), new GameSceneController(application));
    }
}
