package flowerforce.view.game;

import java.io.IOException;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

/**
 * Models a generic FlowerForceScene.
 */
public abstract class AbstractFlowerForceScene implements FlowerForceScene {

    private Scene scene;

    /**
     * 
     * @param fxmlPath the path of the FXML file to load
     * @param imgName the image name to pass to getScaledScene method
     * @param controller the scene's controller to pass to the FXML Loader
     * @throws IOException
     */
    protected AbstractFlowerForceScene(final String fxmlPath, final String imgName, final Object controller) {
        try {
            final FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ClassLoader.getSystemResource(fxmlPath));
            loader.setController(controller);
            final AnchorPane root = loader.load();
            this.scene = FlowerForceApplication.getScaledScene(root, imgName);
        } catch (IOException e) {
            throw new IllegalStateException("All fxmls loaded in the program must be present!");
        } 
    }

    /**
     * {@inheritDoc}
     */
    @SuppressFBWarnings(
        value = "EI",
        justification = "The scene returned was created to be requested by other classes"
    )
    @Override
    public Scene getScene() {
        return this.scene;
    }

}
