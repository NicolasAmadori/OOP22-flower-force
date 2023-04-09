package flowerforce.view.game;

/**
 * Models the Menu screen.
 */
public class MenuScene extends AbstractFlowerForceScene {

    private static final String FXML_FILE_NAME = "SimpleMenu.fxml";

    /**
     * Creates a new Menu view.
     * @param application the application that displays the scene
     */
    public MenuScene(final FlowerForceApplication application) {
        super(FXML_FILE_NAME, new MenuSceneController(application));
    }

}
