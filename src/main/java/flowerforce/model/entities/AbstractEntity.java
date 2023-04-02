package flowerforce.model.entities;

import javafx.geometry.Point2D;

/**
 * Represents a generic entity.
 */
public abstract class AbstractEntity implements Entity {

    private final String entityName;
    private Point2D pos;

    /**
     * 
     * @param pos the position of the entity
     * @param entityName the name of the entity
     */
    protected AbstractEntity(final Point2D pos, final String entityName) {
        this.pos = pos;
        this.entityName = entityName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point2D getPosition() {
        return this.pos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.entityName;
    }

    /**
     * Called by subclasses to update entity's position.
     * @param newPos the position to update
     */
    protected void setPosition(final Point2D newPos) {
        this.pos = newPos;
    }
}
