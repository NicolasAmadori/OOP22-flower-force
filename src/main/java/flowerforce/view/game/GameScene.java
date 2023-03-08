package flowerforce.view.game;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class GameScene implements FlowerForceScene {

    private static final String FXML_PATH = "flowerforce/game/Garden.fxml";
    private final Scene scene;
    private final FXMLLoader loader;
    private final FlowerForceView application;
    private final GameSceneController controller;

    public GameScene(final FlowerForceView application) throws Exception {
        this.application = application;
        this.loader = new FXMLLoader();
        this.controller = new GameSceneController();
        this.loader.setController(controller);
        this.loader.setLocation(ClassLoader.getSystemResource(FXML_PATH));
        final Parent root = this.loader.load();
        this.scene = new Scene(root);
    }

    @Override
    public Scene getScene() {
        return this.scene;
    }
}
