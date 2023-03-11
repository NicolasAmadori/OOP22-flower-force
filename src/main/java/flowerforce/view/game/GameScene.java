package flowerforce.view.game;

import java.awt.Dimension;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.Optional;

public class GameScene implements FlowerForceScene {

    private static final String FXML_PATH = "flowerforce/game/fxml/Garden.fxml";
    private final Scene scene;
    private final GameSceneController sceneController;

    public GameScene(final FlowerForceApplication application, final Dimension size) throws IOException {
        final FXMLLoader loader = new FXMLLoader();
        this.sceneController = new GameSceneController(application, size);
        loader.setLocation(ClassLoader.getSystemResource(FXML_PATH));
        loader.setController(this.sceneController);

        final Parent root = loader.load();

        this.scene = new Scene(root);
    }

    @Override
    public Scene getScene() {
        return this.scene;
    }

    @Override
    public Optional<GameEngine> getGameEngine() {
        if (this.sceneController instanceof GameEngine) {
            return Optional.of((GameEngine) this.sceneController);
        } else {
            return Optional.empty();
        }
    }
}
