package flowerforce.model.entities;

import flowerforce.common.Timer;
import javafx.geometry.Point2D;

/**
 * Represents a generic living entity.
 */
public class LivingEntityImpl implements LivingEntity {

    private double health = 100.0;
    private final Point2D pos;
    private final Timer timer;

    /**
     * 
     * @param pos the initial position to place the entity in
     * @param timer used to produce bullets/suns at the right time
     */
    protected LivingEntityImpl(final Point2D pos, final Timer timer) {
        this.pos = pos;
        this.timer = timer;
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
    public boolean isOver() {
        return this.health <= 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getHealth() {
        return this.health;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void receiveDamage(final double damage) {
        this.health -= damage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        this.timer.updateState();
    }


}
