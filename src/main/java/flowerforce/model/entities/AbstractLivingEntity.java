package flowerforce.model.entities;

import flowerforce.model.utilities.Timer;
import javafx.geometry.Point2D;

/**
 * Represents a generic living entity.
 */
public abstract class AbstractLivingEntity extends AbstractEntity implements LivingEntity {

    private double health;
    private final Timer timer;


    /**
     * 
     * @param pos the initial position to place the entity in
     * @param timer used to produce bullets/suns at the right time
     * @param health the starting health of the entity
     */
    protected AbstractLivingEntity(final Point2D pos, final Timer timer, final double health) {
        super(pos);
        this.timer = timer;
        this.health = health;
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
    public void updateState() {
        this.timer.updateState();
    }

    /**
     * @return entity's timer.
     */
    protected Timer getTimer() {
        return this.timer;
    }

}
