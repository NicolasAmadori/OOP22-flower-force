package flowerforce.view.entities;

import flowerforce.model.entities.Bullet;
import flowerforce.model.entities.Plant;
import flowerforce.model.entities.Zombie;
import flowerforce.model.game.Yard;
import flowerforce.model.game.YardImpl;
import javafx.geometry.Point2D;

import java.io.File;

public final class EntityConverter {
    private final static String IMAGES_FOLDER_PATH = "src" + File.separator + "main" + File.separator + "resources"
            + File.separator + "flowerforce" + File.separator + "game" + File.separator + "images";

    private final static String IMAGES_EXTENSION = ".gif";

    public static EntityView getEntityView(Plant p) {
        final Point2D newPosition = convertPlantPosition(p.getPosition());
        final String completeimagePath = IMAGES_FOLDER_PATH + File.separator
                + p.getPlantType().name().toLowerCase() + IMAGES_EXTENSION;
        return new EntityViewImpl(newPosition, completeimagePath);
    }

    public static EntityView getEntityView(Zombie z) {
        final Point2D newPosition = convertZombiePosition(z.getPosition());
        final String completeImagePath = IMAGES_FOLDER_PATH + File.separator
                + z.getZombieType().name().toLowerCase() + IMAGES_EXTENSION;
        return new EntityViewImpl(newPosition, completeImagePath);
    }

    public static EntityView getEntityView(Bullet b) {
        final Point2D newPosition = convertBulletPosition(b.getPosition());
        final String bulletName = getName(b.getClass().getName().toLowerCase());
        final String completeImagePath = IMAGES_FOLDER_PATH + File.separator
                + bulletName + IMAGES_EXTENSION;
        return new EntityViewImpl(newPosition, completeImagePath);
    }

    private static String getName(String completePackage) {
        String[] splitted = completePackage.split("\\.");
        return splitted[splitted.length - 1];
    }
    private static Point2D convertPlantPosition(Point2D originalPosition) {
        final Yard yardInfo = new YardImpl();
        return originalPosition.subtract(yardInfo.getCellDimension().getWidth(), yardInfo.getCellDimension().getHeight() / 2);
    }

    private static Point2D convertZombiePosition(Point2D originalPosition) {
        final Yard yardInfo = new YardImpl();
        return originalPosition.subtract(0, yardInfo.getCellDimension().getHeight() / 2);
    }

    private static Point2D convertBulletPosition(Point2D originalPosition) {
        final Yard yardInfo = new YardImpl();
        return originalPosition.subtract(yardInfo.getCellDimension().getWidth(), yardInfo.getCellDimension().getHeight() / 2);
    }
}
