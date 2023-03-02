package flowerforce.model.entities;

import flowerforce.common.TimerImpl;
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
        super(pos, new TimerImpl(getStandardSpeed()), getStandardDamage());
    }

}
