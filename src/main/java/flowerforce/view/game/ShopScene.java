package flowerforce.view.game;

/**
 * Models the scene of the shop, extends {@link AbstractFlowerForceScene}.
 */
public class ShopScene extends AbstractFlowerForceScene {
    private static final String FXML_PATH = "flowerforce/game/fxml/Shop.fxml";
    private static final String IMAGE_NAME = "shopBackground.png";

    /**
      * @param application which sets the scene
     */
    protected ShopScene(final FlowerForceApplication application) {
        super(FXML_PATH, IMAGE_NAME, new ShopSceneController(application));
    }
}
