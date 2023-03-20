package flowerforce.model.entities;

import javafx.geometry.Point2D;

/**
 * Models a generic bullet.
 */
public abstract class AbstractBullet extends AbstractEntity implements Bullet {

    private static final double STANDARD_DAMAGE = 40.0;
    private static final int DELTA = 10;
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
