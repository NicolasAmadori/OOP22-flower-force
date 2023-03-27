package flowerforce.model.entities;

import javafx.geometry.Point2D;

/**
 * Models a fire bullet that does additional damage but de-freezes a zombie when hit.
 */
public class FireBullet extends AbstractBullet {

    private static final double ADDITIONAL_DAMAGE = AbstractBullet.getStandardDamage() / 2;

    /**
     * 
     * @param pos the position to place the bullet in
     */
    public FireBullet(final Point2D pos) {
        super(pos, AbstractBullet.getStandardDamage() + ADDITIONAL_DAMAGE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hit(final Zombie zombie) {
        super.hit(zombie);
        zombie.warmUp();
    }

}
