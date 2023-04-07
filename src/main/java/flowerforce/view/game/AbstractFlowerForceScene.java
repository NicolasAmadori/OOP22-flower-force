package flowerforce.view.game;

import java.io.IOException;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

/**
 * Models an abstract {@link FlowerForceScene}.
 */
public abstract class AbstractFlowerForceScene implements FlowerForceScene {

    private Scene scene;

    /**
     * 
     * @param fxmlName the path of the FXML file to load
     * @param imgName the image name to pass to getScaledScene method
     * @param controller the scene's controller to pass to the FXML Loader
     */
    protected AbstractFlowerForceScene(final String fxmlName, final String imgName, final Object controller) {
        try {
            final FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ClassLoader.getSystemResource(fxmlName));
            loader.setController(controller);
            final AnchorPane root = loader.load();
            this.scene = FlowerForceApplication.getScaledScene(root, imgName);
        } catch (IOException e) {
            Platform.exit();
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
