package flowerforce.model.entities;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import flowerforce.common.Timer;
import javafx.geometry.Point2D;

/**
 * Models a generical plant that shoots bullets.
 */
public class ShootingPlantImpl extends AbstractLivingEntity implements ShootingPlant {

    private final Class<?> bulletClass;
    private boolean canShoot;

    /**
     * 
     * @param pos the initial position to place the entity in
     * @param timer used to produce bullets/suns at the right time
     * @param health the starting health of the entity
     * @param bulletClass the class of the bullet type to generate
     */
    public ShootingPlantImpl(final Point2D pos, final Timer timer, final double health, final Class<?> bulletClass) {
        super(pos, timer, health);
        this.bulletClass = bulletClass;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Bullet> nextBullet() {
        Bullet bullet;
        try {
            final Constructor<?> constr = this.bulletClass.getConstructor(Point2D.class);
            bullet = (Bullet) constr.newInstance();
        } catch (
            InvocationTargetException
            | SecurityException
            | NoSuchMethodException
            | InstantiationException 
            | IllegalAccessException 
            | IllegalArgumentException e
        ) {
            return Optional.empty();
        }
        final Optional<Bullet> optBullet = Optional.of(bullet).filter(e -> this.canShoot);
        this.canShoot = false;
        this.getTimer().reset();
        return optBullet;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateState() {
        if (this.getTimer().isReady()) {
            this.canShoot = true;
        }
        super.updateState();
    }

}
