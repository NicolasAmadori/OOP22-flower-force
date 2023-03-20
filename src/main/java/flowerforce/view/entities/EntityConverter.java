package flowerforce.view.entities;

import flowerforce.model.entities.Bullet;
import flowerforce.model.entities.Plant;
import flowerforce.model.entities.Zombie;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import java.io.File;
import java.util.Locale;

/**
 * This utility class convert an entity given from the model into an entity that can be drawn in the view.
 */
public final class EntityConverter {
    private static final String IMAGES_FOLDER_PATH = System.getProperty("user.dir") + File.separator
            + "src" + File.separator + "main" + File.separator + "resources"
            + File.separator + "flowerforce" + File.separator + "game" + File.separator + "images";

    private static final String IMAGES_EXTENSION = ".gif";

    private static final String CARD_EXTENSION = ".png";

    private EntityConverter() {

    }

    /**
     * Convert a plant into an entityView.
     * @param p The plant instance to convert
     * @return The entityView representing the plant
     */
    public static EntityView getEntityView(final Plant p) {
        final String completeImagePath = IMAGES_FOLDER_PATH + File.separator
                + p.getPlantType().name().toLowerCase(Locale.getDefault()) + IMAGES_EXTENSION;
        final Point2D newPosition = convertPlantPosition(p.getPosition(), completeImagePath);
        return new EntityViewImpl(newPosition, completeImagePath);
    }

    /**
     * Convert a zombie into an entityView.
     * @param z The zombie instance to convert
     * @return The entityView representing the zombie
     */
    public static EntityView getEntityView(final Zombie z) {
        final String completeImagePath = IMAGES_FOLDER_PATH + File.separator
                + z.getZombieType().name().toLowerCase(Locale.getDefault()) + IMAGES_EXTENSION;
        final Point2D newPosition = convertZombiePosition(z.getPosition(), completeImagePath);
        return new EntityViewImpl(newPosition, completeImagePath);
    }

    /**
     * Convert a bullet into an entityView.
     * @param b The bullet instance to convert
     * @return The entityView representing the bullet
     */
    public static EntityView getEntityView(final Bullet b) {
        final String bulletName = getName(b.getClass().getName().toLowerCase(Locale.getDefault()));
        final String completeImagePath = IMAGES_FOLDER_PATH + File.separator
                + bulletName + IMAGES_EXTENSION;
        final Point2D newPosition = convertBulletPosition(b.getPosition(), completeImagePath);
        return new EntityViewImpl(newPosition, completeImagePath);
    }

    public static CardView getCardView(final Plant p) {
        final String completeImagePath = IMAGES_FOLDER_PATH + File.separator
                + p.getPlantType().name().toLowerCase(Locale.getDefault()) + CARD_EXTENSION;
        return new CardViewImpl(p.getPlantType().getCost(), completeImagePath);
    }

    private static String getName(final String completePackage) {
        final String[] splitted = completePackage.split("\\.");
        return splitted[splitted.length - 1];
    }
    private static Point2D convertPlantPosition(final Point2D originalPosition, final String imagePath) {
        return originalPosition.subtract(getImageWidth(imagePath), getImageHeight(imagePath) / 2);
    }

    private static Point2D convertZombiePosition(final Point2D originalPosition, final String imagePath) {
        return originalPosition.subtract(0, getImageHeight(imagePath) / 2);
    }

    private static Point2D convertBulletPosition(final Point2D originalPosition, final String imagePath) {
        return originalPosition.subtract(getImageWidth(imagePath), getImageHeight(imagePath) / 2);
    }

    private static double getImageWidth(final String path) {
        return getImageDimension(path, true);
    }

    private static double getImageHeight(final String path) {
        return getImageDimension(path, false);
    }

    private static double getImageDimension(final String path, final boolean isWidth) {
        if(path == null) {
            return -1;
        }
        try {
            return isWidth ? new Image(path).getWidth() : new Image(path).getHeight();
        } catch (IllegalArgumentException e) {
            return -1;
        }
    }
}
