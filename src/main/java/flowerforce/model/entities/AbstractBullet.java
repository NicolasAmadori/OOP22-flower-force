package flowerforce.model.entities;

import flowerforce.common.Timer;
import javafx.geometry.Point2D;

/**
 * Models a generic bullet.
 */
public abstract class AbstractBullet extends AbstractActiveEntity implements Bullet {

    private static final double STANDARD_DAMAGE = 40.0;
    private static final int STANDARD_SPEED = 10;
    private boolean hasHit;

    /**
     * 
     * @param pos the initial position to place the bullet in
     * @param timer used to move at the right time
     * @param damage the damage that the bullet does to zombies
     */
    protected AbstractBullet(final Point2D pos, final Timer timer, final double damage) {
        super(pos, timer, damage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void move() {
        this.setPosition(new Point2D(this.getPosition().getX(), this.getPosition().getY() + 1));
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
    public double getDamage() {
        this.hasHit = true;
        return super.getDamage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hit(final Zombie zombie) {
        zombie.receiveDamage(this.getDamage());
    }

    /**
     * 
     * @return the damage that a generic bullet does to a zombie.
     */
    protected static double getStandardDamage() {
        return STANDARD_DAMAGE;
    }

    /**
     * 
     * @return the standard speed (in game loop cycles) of the bullet
     */
    protected static int getStandardSpeed() {
        return STANDARD_SPEED;
    }

}
