package flowerforce.view.entities;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class SunflowerView implements EntityView {

    private static final String IMAGE_PATH = "flowerforce/game/images/sunflower.gif";
    private final Image image;
    private final Point2D position;

    public SunflowerView(final Point2D placingPosition) throws FileNotFoundException {
        this.position = placingPosition;
        this.image = new Image(ClassLoader.getSystemResourceAsStream(IMAGE_PATH));
    }

    @Override
    public Point2D getPlacingPosition() {
        return this.position;
    }

    @Override
    public Image getImage() {
        return this.image;
    }
    
}
