package flowerforce.model.entities;

import javafx.geometry.Point2D;

/**
 * Models a factory for Bullet entities.
 */
public interface BulletFactory {

    /**
     * Creates a standard bullet.
     * @param pos the position to place the bullet in
     * @return a standard bullet in the given position
     */
    Bullet createStandardBullet(Point2D pos);

    /**
     * Creates a fire bullet.
     * @param pos the position to place the bullet in
     * @return a fire bullet in the given position
     */
    Bullet createFireBullet(Point2D pos);

    /**
     * Creates a snow bullet.
     * @param pos the position to place the bullet in
     * @return a snow bullet in the given position
     */
    Bullet createSnowdBullet(Point2D pos);

}
