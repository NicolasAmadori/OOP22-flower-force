package flowerforce.model.entities;

import flowerforce.model.game.Yard;
import flowerforce.model.utilities.RenderingInformation;
import javafx.geometry.Point2D;

/**
 * Models a generic bullet.
 */
public abstract class AbstractBullet extends AbstractEntity implements Bullet {

    private static final double STANDARD_DAMAGE = 20.0;
    private static final double SECS_PER_CELL = 0.5;
    private static final int TICKS_PER_CELL = (int) (SECS_PER_CELL * RenderingInformation.getFramesPerSecond());
    private static final int DELTA = (int) (Yard.getCellDimension().getWidth() / TICKS_PER_CELL);
    private boolean hasHit;
    private final double damage;

    /**
     * 
     * @param pos the initial position to place the bullet in
     * @param damage the damage that the bullet does to zombies
     */
    protected AbstractBullet(final Point2D pos, final double damage) {
        super(pos);
        this.damage = damage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void move() {
        this.setPosition(new Point2D(this.getPosition().getX() + DELTA, this.getPosition().getY()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOver() {
        return this.hasHit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hit(final Zombie zombie) {
        this.hasHit = true;
        zombie.receiveDamage(this.damage);
    }

    /**
     * 
     * @return the damage that a generic bullet does to a zombie.
     */
    protected static double getStandardDamage() {
        return STANDARD_DAMAGE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDeltaMovement() {
        return DELTA;
    }

}
