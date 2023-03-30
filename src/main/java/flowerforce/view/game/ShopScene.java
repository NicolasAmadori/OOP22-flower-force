package flowerforce.view.game;

import java.io.IOException;

public class ShopScene extends AbstractFlowerForceScene {
    private static final String FXML_PATH = "flowerforce/game/fxml/Shop.fxml";
    private static final String IMAGE_NAME = "shopBackground.png";

    /**
      * @param application which sets the scene
      * @throws IOException if some files couldn't be found
     */
    protected ShopScene(final FlowerForceApplication application) throws IOException {
        super(FXML_PATH, IMAGE_NAME, new GameSceneController(application));
    }
}
