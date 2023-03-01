package flowerforce.model.entities;

import flowerforce.common.Timer;
import javafx.geometry.Point2D;

/**
 * Represents a generical active entity.
 */
public abstract class AbstractActiveEntity extends AbstractEntity implements ActiveEntity {

    private Point2D pos;
    private final double damage;

    /**
     * 
     * @param pos the initial position to place the entity in
     * @param timer used to move at the right time
     * @param damage the damage that the entity does to enemies
     */
    protected AbstractActiveEntity(final Point2D pos, final Timer timer, final double damage) {
        super(timer);
        this.pos = pos;
        this.damage = damage;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public double getDamage() {
        return this.damage;
    }

}
