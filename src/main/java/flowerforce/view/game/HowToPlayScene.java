package flowerforce.view.game;

public class HowToPlayScene extends AbstractFlowerForceScene {
    private static final String FXML_PATH = "flowerforce/game/fxml/HowToPlay.fxml";
    private static final String IMAGE_NAME = "howtoplay.png";

    /**
     * Create a How To Play View
     * @param application The application that displays the scene
     * @throws IOException
     */
    protected HowToPlayScene(final FlowerForceApplication application) {
        super(FXML_PATH, IMAGE_NAME, new HowToPlaySceneController(application));
    }
}
