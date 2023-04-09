package flowerforce.model.entities;

import java.util.Optional;
import java.util.function.Supplier;

import flowerforce.model.utilities.Timer;
import javafx.geometry.Point2D;

/**
 * This is an implementation of {@link ShootingPlant}.
 */
public class ShootingPlantImpl extends AbstractPlant implements ShootingPlant {

    private final Supplier<Bullet> bulletProducer;
    private boolean canShoot;

    /**
     * 
     * @param pos the initial position to place the entity in
     * @param timer used to produce bullets/suns at the right time
     * @param health the starting health of the entity
     * @param bulletProducer the producer of the bullets for this plant
     * @param cost plant's cost
     * @param rechargeTime plant's recharge time
     * @param plantName plant's name
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
        final Optional<Bullet> optBullet = Optional.of(this.canShoot)
                                    .filter(p -> p)
                                    .map(x -> this.bulletProducer.get());
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
