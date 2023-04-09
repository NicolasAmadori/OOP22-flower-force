package flowerforce.view.game;

/**
 * Models the scene of the shop, extends {@link AbstractFlowerForceScene}.
 */
public class ShopScene extends AbstractFlowerForceScene {
    private static final String FXML_FILE_NAME = "Shop.fxml";

    /**
     * @param application which sets the scene
     */
    protected ShopScene(final FlowerForceApplication application) {
        super(FXML_FILE_NAME, new ShopSceneController(application));
    }
}
