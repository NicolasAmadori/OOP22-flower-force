package flowerforce.view.game;

import flowerforce.common.ResourceFinder;

public class ShopScene extends AbstractFlowerForceScene {
    private static final String FXML_FILE_NAME = "Shop.fxml";
    private static final String IMAGE_NAME = "shopBackground.png";

    /**
     * @param application which sets the scene
     */
    protected ShopScene(final FlowerForceApplication application) {
        super(ResourceFinder.getFXMLPath(FXML_FILE_NAME), IMAGE_NAME, new ShopSceneController(application));
    }
}
