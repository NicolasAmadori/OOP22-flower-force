package flowerforce.model.entities.zombies;

import flowerforce.model.entities.AbstractLivingEntity;
import flowerforce.model.entities.plants.Plant;
import flowerforce.model.utilities.RenderingInformation;
import flowerforce.model.utilities.Timer;
import flowerforce.model.utilities.TimerImpl;
import javafx.geometry.Point2D;

/**
 * Implementation of {@link Zombie}.
 */
public class ZombieImpl extends AbstractLivingEntity implements Zombie {

    private static final int FREEZE_FACTOR = 2;
    private static final int FREEZE_WAITING_SECS = 9;
    private static final int EAT_WAITING_SECS = 1;
    private static final int FREEZE_WAITING_TICKS = RenderingInformation.convertSecondsToCycles(FREEZE_WAITING_SECS);
    private static final int EAT_WAITING_TICKS = RenderingInformation.convertSecondsToCycles(EAT_WAITING_SECS);
    private final int damage;
    private final Timer freezeTimer;
    private final int difficulty;
    private boolean isFrozen;
    private boolean canBite;
    private double defaultDelta;
    private double delta;

    /** 
     * @param defaultDelta is the space traveled by the zombie every move update
     * @param damage given by the zombie
     * @param health of the zombie
     * @param position of the zombie
     * @param difficulty the generic difficulty of the zombie
     * @param zombieName the name of the zombie
     */
    protected ZombieImpl(final double defaultDelta, final int damage, final int health, final Point2D position,
            final int difficulty, final String zombieName) {
        super(position, new TimerImpl(EAT_WAITING_TICKS), health, zombieName);
        this.defaultDelta = defaultDelta;
        this.damage = damage;
        this.freezeTimer = new TimerImpl(FREEZE_WAITING_TICKS);
        this.difficulty = difficulty;
        this.isFrozen = false;
        this.canBite = true;
        this.delta = defaultDelta;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void move() {
        super.setPosition(super.getPosition().subtract(this.delta, 0));
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
        this.freezeTimer.reset();
        if (!this.isFrozen) {
            this.delta = this.defaultDelta / FREEZE_FACTOR;
            super.getTimer().setNumCycles(EAT_WAITING_TICKS * FREEZE_FACTOR);
            this.isFrozen = true;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warmUp() {
        if (this.isFrozen) {
            this.freezeTimer.reset();
            this.delta = this.defaultDelta;
            super.getTimer().setNumCycles(EAT_WAITING_TICKS);
            this.isFrozen = false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean manageEating(final Plant plant) {
        if (this.canBite) {
            plant.receiveDamage(this.damage);
            super.getTimer().reset();
            this.canBite = false;
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getDeltaMovement() {
        return this.delta;
    }

    /**
     * This method can be called by subtypes to change the total velocity of the zombie (i.e.
     * its moving and eating velocity)
     * @param accelerationFactor used to change zombie velocity
     */
    protected void changeVelocity(final double accelerationFactor) {
            final double newDelta = this.delta * accelerationFactor;
            final int newNumCycles = RenderingInformation.convertSecondsToCycles(EAT_WAITING_SECS / accelerationFactor);
            this.delta = this.isFrozen ? newDelta / FREEZE_FACTOR : newDelta;
            this.defaultDelta = newDelta;
            super.getTimer().setNumCycles(this.isFrozen ? newNumCycles * FREEZE_FACTOR : newNumCycles);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDifficulty() {
        return this.difficulty;
    }

}
