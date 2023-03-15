package flowerforce.view.entities;

import javafx.geometry.Point2D;

/**
 * This is an implementation of {@link EntityView}
 */
public class EntityViewImpl implements EntityView {
    private final String pathImage;
    private final Point2D position;
    private final int cost;

    /**
     * @param placingPosition position of the entity in the field
     * @param pathImage of the entity to show
     * @param cost of the entity
     */
    public EntityViewImpl(final Point2D placingPosition, final String pathImage, final int cost) {
        this.pathImage = pathImage;
        this.cost = cost;
        this.position = placingPosition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int cost() {
        return this.cost;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String pathImage() {
        return this.pathImage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point2D getPlacingPosition() {
        return this.position;
    }
    
}
