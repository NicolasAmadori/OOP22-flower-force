package flowerforce.model.entities;

import javafx.geometry.Point2D;

/**
 * Models a snow bullet that freezes a zombie after a hit.
 */
public class SnowBullet extends StandardBullet {

    /**
     * 
     * @param pos the position to place the bullet in
     */
    public SnowBullet(final Point2D pos) {
        super(pos);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hit(final Zombie zombie) {
        super.hit(zombie);
        zombie.freeze();
    }

}
