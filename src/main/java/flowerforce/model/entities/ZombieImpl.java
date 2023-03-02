package flowerforce.model.entities;

import flowerforce.common.Timer;
import flowerforce.common.TimerImpl;
import javafx.geometry.Point2D;

/**
 * Implementation of {@link Zombie}.
 */
public class ZombieImpl implements Zombie {

    private static final double FREEZE_FACTOR = 0.5;
    private static final int FREEZE_TIME_UNIT = 10; //updates for each delta
    private static final int EATING_TIME_UNIT = 2;
    private final int delta;
    private final double damage;
    private final Timer eatingTimer;
    private final Timer freezeTimer;
    private double health;
    private Point2D position;  
    private boolean isFrozen;
    private boolean canBite;

    /** 
     * @param delta is the space traveled by the zombie every move update
     * @param damage given by the zombie
     * @param health of the zombie
     * @param position of the zombie
     */
    protected ZombieImpl(final int delta, final double damage, final double health, final Point2D position) {
        this.delta = delta;
        this.damage = damage;
        this.freezeTimer = new TimerImpl(this.delta*FREEZE_TIME_UNIT);
        this.eatingTimer = new TimerImpl(this.delta*EATING_TIME_UNIT);
        this.health = health;
        this.position = position;        
        this.isFrozen = false;
        this.canBite = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void move() {
        this.position = this.position.subtract(this.isFrozen ? (int)(this.delta*FREEZE_FACTOR) : this.delta, 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getDamage() {
        return this.damage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point2D getPosition() {
        return this.position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOver() {
        return this.health <= 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        if (this.isFrozen) {
            this.freezeTimer.updateState();
            if (this.freezeTimer.isReady()) {                
                this.warmUp();
            }
        }
        this.eatingTimer.updateState();
        if (this.eatingTimer.isReady()) {
            this.canBite = true;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getHealth() {
        return this.health;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void receiveDamage(double damage) {
        this.health -= damage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void freeze() {
        this.isFrozen = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warmUp() {
        this.freezeTimer.reset();
        this.isFrozen = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean bite() {
        if (this.canBite) {
            this.eatingTimer.reset();
        }
        return this.canBite;
    }

}
