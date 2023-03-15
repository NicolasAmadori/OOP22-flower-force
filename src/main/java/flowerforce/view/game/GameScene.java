package flowerforce.view.game;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class GameScene implements FlowerForceScene {

    private static final String FXML_PATH = "flowerforce/game/fxml/Garden.fxml";
    private static final String IMAGE_NAME = "Garden.png";
    private final Scene scene;

    public GameScene(final FlowerForceApplication application) throws IOException {
        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ClassLoader.getSystemResource(FXML_PATH));
        loader.setController(new GameSceneController(application));

        final AnchorPane root = loader.load();

        this.scene = FlowerForceApplication.getScaledScene(root, IMAGE_NAME);
    }

    @Override
    public Scene getScene() {
        return this.scene;
    }
}
