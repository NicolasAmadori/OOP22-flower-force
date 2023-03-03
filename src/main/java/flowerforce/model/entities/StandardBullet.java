package flowerforce.model.entities;

import javafx.geometry.Point2D;

/**
 * Models a standard bullet.
 */
public class StandardBullet extends AbstractBullet {

    /**
     * 
     * @param pos the position to place the bullet in
     */
    public StandardBullet(final Point2D pos) {
        super(pos, getStandardDamage());
    }

}
