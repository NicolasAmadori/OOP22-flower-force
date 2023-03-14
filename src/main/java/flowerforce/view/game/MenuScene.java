package flowerforce.view.game;

import java.io.IOException;

import flowerforce.controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;

/**
 * Models the Menu screen.
 */
public class MenuScene implements FlowerForceScene {

    private static final String FXML_PATH = "flowerforce/game/fxml/SimpleMenu.fxml";
    private final Scene scene;

    /**
     * Creates a new Menu view.
     * @param application the application that displays the scene
     * @param mainController the main controller
     * @throws IOException
     */
    public MenuScene(final FlowerForceApplication application, final Controller mainController) throws IOException {
        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ClassLoader.getSystemResource(FXML_PATH));
        loader.setController(new MenuSceneController(application, mainController));
        final AnchorPane root = loader.load();

        this.scene = FlowerForceApplication.getScaledScene(root, "menu.png");
    }

    /**
     * Returns the class' scene.
     */
    @Override
    public Scene getScene() {
        return this.scene;
    }

}
