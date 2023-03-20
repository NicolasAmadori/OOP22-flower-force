package flowerforce.view.entities;

import flowerforce.model.entities.Bullet;
import flowerforce.model.entities.Plant;
import flowerforce.model.entities.Zombie;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import java.io.File;

public final class EntityConverter {
    private final static String IMAGES_FOLDER_PATH = System.getProperty("user.dir") + File.separator
            + "src" + File.separator + "main" + File.separator + "resources"
            + File.separator + "flowerforce" + File.separator + "game" + File.separator + "images";

    private final static String IMAGES_EXTENSION = ".gif";

    public static EntityView getEntityView(Plant p) {
        final String completeimagePath = IMAGES_FOLDER_PATH + File.separator
                + p.getPlantType().name().toLowerCase() + IMAGES_EXTENSION;
        final Point2D newPosition = convertPlantPosition(p.getPosition(), completeimagePath);
        return new EntityViewImpl(newPosition, completeimagePath);
    }

    public static EntityView getEntityView(Zombie z) {
        final String completeImagePath = IMAGES_FOLDER_PATH + File.separator
                + z.getZombieType().name().toLowerCase() + IMAGES_EXTENSION;
        final Point2D newPosition = convertZombiePosition(z.getPosition(), completeImagePath);
        return new EntityViewImpl(newPosition, completeImagePath);
    }

    public static EntityView getEntityView(Bullet b) {
        final String bulletName = getName(b.getClass().getName().toLowerCase());
        final String completeImagePath = IMAGES_FOLDER_PATH + File.separator
                + bulletName + IMAGES_EXTENSION;
        final Point2D newPosition = convertBulletPosition(b.getPosition(), completeImagePath);
        return new EntityViewImpl(newPosition, completeImagePath);
    }

    private static String getName(String completePackage) {
        String[] splitted = completePackage.split("\\.");
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
        try{
            return new Image(path).getWidth();
        }
        catch(Exception ex) {
            return -1;
        }
    }

    private static double getImageHeight(final String path) {
        try{
            return new Image(path).getHeight();
        }
        catch(Exception ex) {
            return -1;
        }
    }
}
