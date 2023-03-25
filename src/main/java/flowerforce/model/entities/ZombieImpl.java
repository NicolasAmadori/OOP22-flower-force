package flowerforce.model.entities;

import flowerforce.common.Timer;
import flowerforce.common.TimerImpl;
import flowerforce.model.entities.IdConverter.Zombies;
import javafx.geometry.Point2D;

/**
 * Implementation of {@link Zombie}.
 */
public class ZombieImpl extends AbstractLivingEntity implements Zombie {

    private static final double FREEZE_FACTOR = 2;
    private static final int FREEZE_WAITING_SECS = 9;
    private static final int EAT_WAITING_SECS = 1;
    private static final int FREEZE_WAITING_TICKS = FREEZE_WAITING_SECS * 30;
    private static final int EAT_WAITING_TICKS = EAT_WAITING_SECS * 30;
    private final int defaultDelta;
    private final double damage;
    private final Timer freezeTimer;
    private final Zombies zombieType;
    private boolean isFrozen;
    private boolean canBite;
    private int delta;

    /** 
     * @param defaultDelta is the space traveled by the zombie every move update
     * @param damage given by the zombie
     * @param health of the zombie
     * @param position of the zombie
     * @param zombieType the type of zombie
     */
    protected ZombieImpl(final int defaultDelta, final double damage, final double health, final Point2D position,
            final Zombies zombieType) {                
        super(position, new TimerImpl(EAT_WAITING_TICKS / defaultDelta), health);
        this.defaultDelta = defaultDelta;
        this.damage = damage;
        this.freezeTimer = new TimerImpl(FREEZE_WAITING_TICKS);
        this.zombieType = zombieType;
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

    private void setEatingTimerNumCycles() {
        super.getTimer().setNumCycles((int) (EAT_WAITING_TICKS / this.delta));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void freeze() {
        this.isFrozen = true;
        this.delta = this.delta <= 1 ? 1 : (int) (this.delta / FREEZE_FACTOR);
        this.setEatingTimerNumCycles();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warmUp() {
        this.freezeTimer.reset();
        this.isFrozen = false;
        this.delta = this.defaultDelta;
        this.setEatingTimerNumCycles();
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

    @Override
    public int getDeltaMovement() {
        return this.delta;
    }

}
