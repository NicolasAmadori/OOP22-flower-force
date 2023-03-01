package flowerforce.model.entities;

import javafx.geometry.Point2D;

public class ZombieImpl implements Zombie {

    private static final int FREEZE_TIME = 100;
    private static final double FREEZE_FACTOR = 0.5;
    private static final int EATING_TIME_UNIT = 2; //updates for each delta
    private final int delta;
    private final double damage;
    private double health;
    private Point2D position;
    private int eatingTime; //updates from one bite to another
    private int freezeCounter;
    private int eatingCounter;    
    private boolean isFrozen;
    private boolean isBiting;
    

    protected ZombieImpl(final int delta, final double damage, final double health, final Point2D position) {
        this.delta = delta;
        this.damage = damage;
        this.health = health;
        this.position = position;
        this.eatingTime = delta*EATING_TIME_UNIT;
        this.freezeCounter = FREEZE_TIME;
        this.eatingCounter = eatingTime;
        this.isFrozen = false;
        this.isBiting = false;
    }

    @Override
    public void move() {
        this.position = this.position.subtract(this.isFrozen ? (int)(this.delta*FREEZE_FACTOR) : this.delta, 0);
    }

    @Override
    public double getDamage() {
        return this.damage;
    }

    @Override
    public Point2D getPosition() {
        return this.position;
    }

    @Override
    public boolean isOver() {
        return this.health <= 0;
    }

    @Override
    public void update() {
        if (this.isFrozen) {
            this.freezeCounter--;
            if (this.freezeCounter <= 0) {                
                this.warmUp();
            }
        }
        this.eatingCounter--;
        if (this.eatingCounter <= 0) {
            this.eatingCounter = this.eatingTime;
            this.isBiting = true;
        } else {
            this.isBiting = false;
        }
    }
    
    @Override
    public double getHealth() {
        return this.health;
    }

    @Override
    public void receiveDamage(double damage) {
        this.health -= damage;
    }

    @Override
    public void freeze() {
        this.isFrozen = true;
    }

    @Override
    public void warmUp() {
        this.freezeCounter = FREEZE_TIME;
        this.isFrozen = false;
    }

    @Override
    public boolean bite() {
        return this.isBiting;
    }
    
}
