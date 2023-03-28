package flowerforce.view.game;

import java.io.IOException;

public class HowToPlayScene extends AbstractFlowerForceScene {
    private static final String FXML_PATH = "flowerforce/game/fxml/HowToPlay.fxml";
    private static final String IMAGE_NAME = "menu.png";

    /**
     * Create a How To Play View
     * @param application The application that displays the scene
     * @throws IOException
     */
    protected HowToPlayScene(final FlowerForceApplication application) throws IOException {
        super(FXML_PATH, IMAGE_NAME, new HowToPlaySceneController(application));
    }
}
