package flowerforce.model.entities;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import flowerforce.model.entities.IdConverter.Plants;
import flowerforce.model.utilities.Timer;
import javafx.geometry.Point2D;

/**
 * Models a generical plant that shoots bullets.
 */
public class ShootingPlantImpl extends AbstractPlant implements ShootingPlant {

    private final Supplier<Bullet> bulletProducer;
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
        final int health,
        final Supplier<Bullet> bulletProducer,
        final int cost,
        final int rechargeTime,
        final String plantName
    ) {
        super(pos, timer, health, cost, rechargeTime, plantName);
        this.bulletProducer = bulletProducer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Bullet> nextBullet() {
        final Optional<Bullet> optBullet = Optional.of(this.bulletProducer.get()).filter(e -> this.canShoot);
        if (this.canShoot) {
            this.canShoot = false;
            this.getTimer().reset();
        }
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
