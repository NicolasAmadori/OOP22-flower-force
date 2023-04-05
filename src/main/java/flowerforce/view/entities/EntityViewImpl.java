package flowerforce.view.entities;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

/**
 * This is an implementation of {@link EntityView}.
 */
public class EntityViewImpl implements EntityView {
    private final String pathPlaceableImage;
    private Point2D position;

    /**
     * @param placingPosition position of the entity in the field
     * @param pathPlaceableImage of the entity to show in the field
     */
    public EntityViewImpl(final Point2D placingPosition, final String pathPlaceableImage) {
        this.pathPlaceableImage = pathPlaceableImage;
        this.position = placingPosition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Image getPlaceableImage() {
        return new Image(pathPlaceableImage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosition(final Point2D position) {
        this.position = position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point2D getPlacingPosition() {
        return this.position;
    }
}
