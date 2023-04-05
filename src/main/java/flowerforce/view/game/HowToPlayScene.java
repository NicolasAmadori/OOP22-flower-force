package flowerforce.view.game;

import flowerforce.common.ResourceFinder;

/**
 * Models the HowToPlay scene.
 */
public class HowToPlayScene extends AbstractFlowerForceScene {
    private static final String FXML_FILE_NAME = "HowToPlay.fxml";
    private static final String IMAGE_NAME = "howtoplay.png";

    /**
     * Create a How To Play View
     * @param application The application that displays the scene
     */
    protected HowToPlayScene(final FlowerForceApplication application) {
        super(ResourceFinder.getFXMLPath(FXML_FILE_NAME), IMAGE_NAME, new HowToPlaySceneController(application));
    }
}
