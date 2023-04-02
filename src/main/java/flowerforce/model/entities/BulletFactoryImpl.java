package flowerforce.model.entities;

import javafx.geometry.Point2D;

/**
 * This is an implementation of {@link BulletFactory}.
 */
public class BulletFactoryImpl implements BulletFactory {

    private static final int STANDARD_DAMAGE = 20;
    private static final int FIRE_DAMAGE = STANDARD_DAMAGE * 2;

    /**
     * {@inheritDoc}
     */
    @Override
    public Bullet createStandardBullet(final Point2D pos) {
        return new BulletImpl(pos, STANDARD_DAMAGE, "standardbullet");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Bullet createFireBullet(final Point2D pos) {
        return new BulletImpl(pos, FIRE_DAMAGE, "firebullet", z -> z.warmUp());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Bullet createSnowdBullet(final Point2D pos) {
        return new BulletImpl(pos, STANDARD_DAMAGE, "snowbullet", z -> z.freeze());
    }

}
