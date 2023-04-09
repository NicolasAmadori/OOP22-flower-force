package flowerforce.view.game;

import flowerforce.common.ResourceFinder;

/**
 * Models the scene of the shop, extends {@link AbstractFlowerForceScene}.
 */
public class ShopScene extends AbstractFlowerForceScene {
    private static final String FXML_FILE_NAME = "Shop.fxml";

    /**
     * @param application which sets the scene
     */
    protected ShopScene(final FlowerForceApplication application) {
        super(ResourceFinder.getFXMLPath(FXML_FILE_NAME), new ShopSceneController(application));
    }
}
