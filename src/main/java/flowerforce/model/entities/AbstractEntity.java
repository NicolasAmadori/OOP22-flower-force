package flowerforce.model.entities;

import javafx.geometry.Point2D;

/**
 * Represents a generical entity.
 */
public abstract class AbstractEntity implements Entity {

    private Point2D pos;

    /**
     * 
     * @param pos the position of the entity
     */
    protected AbstractEntity(final Point2D pos) {
        this.pos = pos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point2D getPosition() {
        return this.pos;
    }

    /**
     * Called by subclasses to update entity's position.
     * @param newPos the position to update
     */
    protected void setPosition(final Point2D newPos) {
        this.pos = newPos;
    }

}
