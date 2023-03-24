package flowerforce.view.entities;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.util.Objects;

/**
 * This is an implementation of {@link EntityView}.
 */
public class EntityViewImpl implements EntityView {
    private final String pathPlaceableImage;
    private final Point2D position;
    private final long ordinal;

    /**
     * @param placingPosition position of the entity in the field
     * @param pathPlaceableImage of the entity to show in the field
     */
    public EntityViewImpl ( final Point2D placingPosition, final String pathPlaceableImage, final long ordinal) {
        this.pathPlaceableImage = pathPlaceableImage;
        this.position = placingPosition;
        this.ordinal = ordinal;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Image getPlaceableImage() {
        return new Image(pathPlaceableImage);
    }

    /**
     * @return {@inheritDoc}
     */
    @Override
    public long getOrdinal() {
        return this.ordinal;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point2D getPlacingPosition() {
        return this.position;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || o instanceof EntityViewImpl) {
            return false;
        }
        final EntityViewImpl that = (EntityViewImpl) o;
        return ordinal == that.ordinal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ordinal);
    }
}
