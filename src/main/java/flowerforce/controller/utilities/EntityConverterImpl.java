package flowerforce.controller.utilities;

import flowerforce.common.ResourceFinder;
import flowerforce.model.entities.EntityInfo;
import flowerforce.view.entities.EntityView;
import flowerforce.view.entities.EntityViewImpl;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

/**
 * This utility class convert an entity given from the model into an entity that can be drawn in the view.
 */
public final class EntityConverterImpl implements EntityConverter {
    private static final String IMAGES_EXTENSION = ".png";

    private static final double BULLET_OFFSET = 85.0 / 880.0;
    private final double yardRatioHeight;
    private final double yardRatioWidth;
    private final double bulletHeightOffset;

    /**
     * Create a new instance of EntityConverter, setting all the information.
     * @param modelYardDimension The dimensione of the yard of the model
     * @param viewYardDimension The dimension of the yard of the view
     */
    public EntityConverterImpl(final Dimension2D modelYardDimension, final Dimension2D viewYardDimension) {
        this.yardRatioHeight = viewYardDimension.getHeight() / modelYardDimension.getHeight();
        this.yardRatioWidth =  viewYardDimension.getWidth() / modelYardDimension.getWidth();
        this.bulletHeightOffset = viewYardDimension.getHeight() * BULLET_OFFSET; //Set proportion for the bullet generation offset
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityView getPlantView(final EntityInfo plantInfo) {
        final String completeImagePath = ResourceFinder.getPlantImagePath(
                plantInfo.getName()g.concat(IMAGES_EXTENSION));
        final Point2D newPosition = convertPlantPosition(plantInfo.getPosition(), completeImagePath);
        return new EntityViewImpl(newPosition, completeImagePath);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityView getZombieView(final EntityInfo zombieInfo) {
        final String completeImagePath = ResourceFinder.getZombieImagePath(
                zombieInfo.getName().concat(IMAGES_EXTENSION));
        final Point2D newPosition = convertZombiePosition(zombieInfo.getPosition(), completeImagePath);
        return new EntityViewImpl(newPosition, completeImagePath);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityView getBulletView(final EntityInfo bulletInfo) {
        final String completeImagePath = ResourceFinder.getBulletImagePath(
                bulletInfo.getName().concat(IMAGES_EXTENSION));
        final Point2D newPosition = convertBulletPosition(bulletInfo.getPosition(), completeImagePath);
        return new EntityViewImpl(newPosition, completeImagePath);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeZombieViewPosition(final EntityView entityView, final Point2D newPosition) {
        entityView.setPosition(convertZombiePosition(newPosition, entityView.getPlaceableImage().getUrl()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeBulletViewPosition(final EntityView entityView, final Point2D newPosition) {
        entityView.setPosition(convertBulletPosition(newPosition, entityView.getPlaceableImage().getUrl()));
    }
    private Point2D convertPlantPosition(final Point2D originalPosition, final String imagePath) {
        //Convert the model position multiplying for the yardSizeFactor
        Point2D outputPosition = new Point2D(
                originalPosition.getX() * this.yardRatioWidth,
                originalPosition.getY() * this.yardRatioHeight);

        //Get the placing position
        outputPosition = outputPosition.subtract(
                getImageWidth(imagePath),
                getImageHeight(imagePath));

        return outputPosition;
    }

    private Point2D convertZombiePosition(final Point2D originalPosition, final String imagePath) {
        //Convert the model position multiplying for the yardSizeFactor
        Point2D outputPosition = new Point2D(
                originalPosition.getX() * this.yardRatioWidth,
                originalPosition.getY() * this.yardRatioHeight);

        //Get the placing position
        outputPosition = outputPosition.subtract(
                0,
                getImageHeight(imagePath));

        return outputPosition;
    }

    private Point2D convertBulletPosition(final Point2D originalPosition, final String imagePath) {
        //Convert the model position multiplying for the yardSizeFactor
        Point2D outputPosition = new Point2D(
                originalPosition.getX() * this.yardRatioWidth,
                originalPosition.getY() * this.yardRatioHeight);

        //Get the placing position
        outputPosition = outputPosition.subtract(
                getImageWidth(imagePath),
                getImageHeight(imagePath));
        return outputPosition.subtract(0, bulletHeightOffset); //set the bullet offset for the bullet
    }

    private static double getImageWidth(final String path) {
        return getImageDimension(path, true);
    }

    private static double getImageHeight(final String path) {
        return getImageDimension(path, false);
    }

    private static double getImageDimension(final String path, final boolean isWidth) {
        if (path == null) {
            return -1;
        }
        try {
            return isWidth ? new Image(path).getWidth() : new Image(path).getHeight();
        } catch (IllegalArgumentException e) {
            return -1;
        }
    }
}
