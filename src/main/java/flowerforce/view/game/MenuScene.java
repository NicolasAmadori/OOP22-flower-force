package flowerforce.view.game;

import java.io.IOException;

/**
 * Models the Menu screen.
 */
public class MenuScene extends AbstractFlowerForceScene {

    private static final String FXML_PATH = "flowerforce/game/fxml/SimpleMenu.fxml";
    private static final String IMAGE_NAME = "menu.png";

    /**
     * Creates a new Menu view.
     * @param application the application that displays the scene
     * @throws IOException
     */
    public MenuScene(final FlowerForceApplication application) throws IOException {
        super(FXML_PATH, IMAGE_NAME, new MenuSceneController(application));
    }

}
