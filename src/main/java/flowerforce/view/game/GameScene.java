package flowerforce.view.game;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class GameScene implements FlowerForceScene {

    private static final String FXML_PATH = "flowerforce/game/fxml/Garden.fxml";
    private final Scene scene;
    public GameScene(final FlowerForceApplication application) throws IOException {
        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ClassLoader.getSystemResource(FXML_PATH));
        loader.setController(new GameSceneController(application));

        final Parent root = loader.load();

        this.scene = new Scene(root);
    }

    @Override
    public Scene getScene() {
        return this.scene;
    }
}
