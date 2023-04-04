package flowerforce.model.entities;

import javafx.geometry.Point2D;

/**
 * Models a factory for Bullet entities.
 */
public class BulletFactory {

    private static final int STANDARD_DAMAGE = 20;
    private static final int FIRE_DAMAGE = STANDARD_DAMAGE * 2;

    private BulletFactory() {
    }

    /**
     * Creates a standard bullet.
     * @param pos the position to place the bullet in
     * @return a standard bullet in the given position
     */
    public static Bullet createStandardBullet(final Point2D pos) {
        return new BulletImpl(pos, STANDARD_DAMAGE, "standardbullet");
    }

    /**
     * Creates a fire bullet.
     * @param pos the position to place the bullet in
     * @return a fire bullet in the given position
     */
    public static Bullet createFireBullet(final Point2D pos) {
        return new BulletImpl(pos, FIRE_DAMAGE, "firebullet", z -> z.warmUp());
    }

    /**
     * Creates a snow bullet.
     * @param pos the position to place the bullet in
     * @return a snow bullet in the given position
     */
    public static Bullet createSnowdBullet(final Point2D pos) {
        return new BulletImpl(pos, STANDARD_DAMAGE, "snowbullet", z -> z.freeze());
    }
}
