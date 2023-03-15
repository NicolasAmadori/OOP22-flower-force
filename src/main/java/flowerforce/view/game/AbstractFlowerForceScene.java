package flowerforce.view.game;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

/**
 * Models a generic FlowerForceScene.
 */
public abstract class AbstractFlowerForceScene implements FlowerForceScene {

    private final Scene scene;

    /**
     * 
     * @param FXMLPath the path of the FXML file to load
     * @param imageName the image name to pass to getScaledScene method
     * @param controller the scene's controller to pass to the FXML Loader
     * @throws IOException
     */
    protected AbstractFlowerForceScene(final String FXMLPath, final String imageName, final Object controller) throws IOException {
        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ClassLoader.getSystemResource(FXMLPath));
        loader.setController(controller);
        final AnchorPane root = loader.load();
        this.scene = FlowerForceApplication.getScaledScene(root, imageName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Scene getScene() {
        return this.scene;
    }

    
}
