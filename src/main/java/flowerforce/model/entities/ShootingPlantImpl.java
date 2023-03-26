package flowerforce.model.entities;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import flowerforce.model.entities.IdConverter.Plants;
import flowerforce.model.utilities.Timer;
import javafx.geometry.Point2D;

/**
 * Models a generical plant that shoots bullets.
 */
public class ShootingPlantImpl extends AbstractPlant implements ShootingPlant {

    private final Class<?> bulletClass;
    private boolean canShoot;

    /**
     * 
     * @param pos the initial position to place the entity in
     * @param timer used to produce bullets/suns at the right time
     * @param health the starting health of the entity
     * @param bulletClass the class of the bullet type to generate
     * @param plantType the type of the plant
     */
    public ShootingPlantImpl(
        final Point2D pos,
        final Timer timer,
        final double health,
        final Class<?> bulletClass,
        final Plants plantType
    ) {
        super(pos, timer, health, plantType);
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
            bullet = (Bullet) constr.newInstance(new Point2D(this.getPosition().getX() + 1, this.getPosition().getY()));
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
        return optBullet;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateState() {
        super.updateState();
        if (this.getTimer().isReady()) {
            this.canShoot = true;
        }
    }

}
