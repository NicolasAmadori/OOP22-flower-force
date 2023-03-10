package flowerforce.view.game;

import java.awt.Dimension;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import flowerforce.view.entities.EntityView;

public class GameScene implements FlowerForceScene, GameEngine {

    private static final String FXML_PATH = "flowerforce/game/fxml/Garden.fxml";
    private final Scene scene;
    private final GameSceneController sceneController;
    private final Set<EntityView> entities = new HashSet<>();

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
    public void addEntity(EntityView entity) {
        entities.add(entity);
    }

    @Override
    public void removeEntity(EntityView entity) {
        entities.remove(entity);
    }

    @Override
    public void clearEntities() {
        entities.clear();
    }

    @Override
    public void render() {
        this.sceneController.clearCanvas();
        entities.forEach(e -> this.sceneController.draw(e.getImage(), e.getPlacingPosition()));
    }
}
