package flowerforce.model.entities;

import flowerforce.common.Timer;
import flowerforce.common.TimerImpl;
import flowerforce.model.entities.IdConverter.Zombies;
import javafx.geometry.Point2D;

/**
 * Implementation of {@link Zombie}.
 */
public class ZombieImpl extends AbstractLivingEntity implements Zombie {

    private static final double FREEZE_FACTOR = 0.5;
    private static final int FREEZE_TIME_UNIT = 10; //updates for each delta unit
    private static final int EATING_TIME_UNIT = 2;
    private final int delta;
    private final int eatingTime;
    private final double damage;
    private final Timer freezeTimer;
    private final Zombies zombieType;
    private boolean isFrozen;
    private boolean canBite;

    /** 
     * @param delta is the space traveled by the zombie every move update
     * @param damage given by the zombie
     * @param health of the zombie
     * @param position of the zombie
     * @param zombieType the type of zombie
     */
    protected ZombieImpl(final int delta, final double damage, final double health, final Point2D position,
            final Zombies zombieType) {                
        super(position, new TimerImpl(delta * EATING_TIME_UNIT), health);
        this.eatingTime = delta * EATING_TIME_UNIT;
        this.delta = delta;
        this.damage = damage;
        this.freezeTimer = new TimerImpl(this.delta * FREEZE_TIME_UNIT);
        this.zombieType = zombieType;
        this.isFrozen = false;
        this.canBite = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void move() {
        super.setPosition(super.getPosition().subtract(this.isFrozen ? (int) (this.delta * FREEZE_FACTOR) : this.delta, 0));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateState() {
        if (this.isFrozen) {
            this.freezeTimer.updateState();
            if (this.freezeTimer.isReady()) {
                this.warmUp();
            }
        }
        if (!this.canBite) {
            super.updateState();
            if (super.getTimer().isReady()) {
                this.canBite = true;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void freeze() {
        this.isFrozen = true;
        super.getTimer().setNumCycles((int) (this.eatingTime * FREEZE_FACTOR));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warmUp() {
        this.freezeTimer.reset();
        this.isFrozen = false;
        super.getTimer().setNumCycles(this.eatingTime);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void manageEating(final Plant plant) {
        if (this.canBite) {
            plant.receiveDamage(this.damage);
            super.getTimer().reset();
            this.canBite = false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Zombies getZombieType() {
        return this.zombieType;
    }

}
