package flowerforce.view.game;

import java.io.IOException;

import flowerforce.controller.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

/**
 * Models the Menu screen.
 */
public class MenuScene implements FlowerForceScene {

    private static final String FXML_PATH = "flowerforce/game/fxml/SimpleMenu.fxml";
    private final Scene scene;

    /**
     * Creates a new Menu view.
     * @param application the application that displays the scene
     * @param gameController the main controller
     * @throws IOException
     */
    public MenuScene(final FlowerForceApplication application, final GameController gameController) throws IOException {
        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ClassLoader.getSystemResource(FXML_PATH));
        loader.setController(new MenuSceneController(application, gameController));
        final AnchorPane root = loader.load();
        this.scene = new Scene(root);
    }

    /**
     * Returns the class' scene.
     */
    @Override
    public Scene getScene() {
        return this.scene;
    }

}
