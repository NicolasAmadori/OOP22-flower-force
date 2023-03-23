package flowerforce.view.entities;

import flowerforce.common.ResourceFinder;
import flowerforce.model.entities.Bullet;
import flowerforce.model.entities.IdConverter;
import flowerforce.model.entities.Plant;
import flowerforce.model.entities.Zombie;
import flowerforce.model.game.Yard;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.awt.*;
import java.util.Locale;

/**
 * This utility class convert an entity given from the model into an entity that can be drawn in the view.
 */
public final class EntityConverter {
    private static final String ANIMATED_IMAGE_EXTENSION = ".gif";
    private static final String STATIC_IMAGE_EXTENSION = ".png";

    private final Dimension2D viewYardDimension;

    private final double yardRatioHeight;

    private final double yardRatioWidth;
    private final double imageResizeFactor;
    public EntityConverter(Dimension2D viewYardDimension, double imageResizeFactor) {
        this.viewYardDimension = viewYardDimension;
        this.imageResizeFactor = imageResizeFactor;

        this.yardRatioHeight = Yard.getYardDimension().getHeight() / this.viewYardDimension.getHeight();
        this.yardRatioWidth = Yard.getYardDimension().getWidth() / this.viewYardDimension.getWidth();
    }

    /**
     * Convert a plant into an entityView.
     * @param p The plant instance to convert
     * @return The entityView representing the plant
     */
    public EntityView getEntityView(final Plant p) {
        final String completeImagePath = ResourceFinder.getImagePath(
                p.getPlantType().name().toLowerCase(Locale.getDefault()).concat(ANIMATED_IMAGE_EXTENSION));
        final Point2D newPosition = convertPlantPosition(p.getPosition(), completeImagePath);
        return new EntityViewImpl(newPosition, completeImagePath);
    }

    /**
     * Convert a zombie into an entityView.
     * @param z The zombie instance to convert
     * @return The entityView representing the zombie
     */
    public EntityView getEntityView(final Zombie z) {
        final String completeImagePath = ResourceFinder.getImagePath(
                z.getZombieType().name().toLowerCase(Locale.getDefault()).concat(ANIMATED_IMAGE_EXTENSION));
        final Point2D newPosition = convertZombiePosition(z.getPosition(), completeImagePath);
        return new EntityViewImpl(newPosition, completeImagePath);
    }

    /**
     * Convert a bullet into an entityView.
     * @param b The bullet instance to convert
     * @return The entityView representing the bullet
     */
    public EntityView getEntityView(final Bullet b) {
        final String bulletName = getName(b.getClass().getName().toLowerCase(Locale.getDefault()));
        final String completeImagePath = ResourceFinder.getImagePath(bulletName.concat(STATIC_IMAGE_EXTENSION));
        final Point2D newPosition = convertBulletPosition(b.getPosition(), completeImagePath);
        return new EntityViewImpl(newPosition, completeImagePath);
    }

    public CardView getCardView(final IdConverter.Plants p) {
        final String completeImagePath = ResourceFinder.getImagePath(
                p.name().toLowerCase(Locale.getDefault()).concat(STATIC_IMAGE_EXTENSION));
        return new CardViewImpl(p.getCost(), completeImagePath);
    }

    private String getName(final String completePackage) {
        final String[] splitted = completePackage.split("\\.");
        return splitted[splitted.length - 1];
    }
    private Point2D convertPlantPosition(final Point2D originalPosition, final String imagePath) {
        Point2D outputPosition = new Point2D(originalPosition.getX() * this.yardRatioWidth, originalPosition.getY() * this.yardRatioHeight);//Convert the model position multiplying for the yardSizeFactor
        outputPosition = outputPosition.subtract(getImageWidth(imagePath) * this.imageResizeFactor, getImageHeight(imagePath) * this.imageResizeFactor);//Get the placing position

        return outputPosition;
    }

    private Point2D convertZombiePosition(final Point2D originalPosition, final String imagePath) {
        Point2D outputPosition = new Point2D(originalPosition.getX() * this.yardRatioWidth, originalPosition.getY() * this.yardRatioHeight);//Convert the model position multiplying for the yardSizeFactor
        outputPosition = outputPosition.subtract(0, getImageHeight(imagePath) * this.imageResizeFactor);//Get the placing position

        return outputPosition;
    }

    private Point2D convertBulletPosition(final Point2D originalPosition, final String imagePath) {
        Point2D outputPosition = new Point2D(originalPosition.getX() * this.yardRatioWidth, originalPosition.getY() * this.yardRatioHeight);//Convert the model position multiplying for the yardSizeFactor
        outputPosition = outputPosition.subtract(getImageWidth(imagePath) * this.imageResizeFactor, getImageHeight(imagePath) * this.imageResizeFactor);//Get the placing position
        return outputPosition.subtract(0, 80);//TODO: modify
//        return outputPosition;
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
