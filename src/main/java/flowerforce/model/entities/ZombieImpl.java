package flowerforce.model.entities;

import javafx.geometry.Point2D;

public class ZombieImpl implements Zombie {

    private static final int FREEZE_TIME = 100;
    private static final double FREEZE_FACTOR = 0.5;
    private final int delta;
    private final double damage;
    private double health;
    private Point2D position;
    private int counter;
    private boolean isFrozen;
    

    protected ZombieImpl(final int delta, final double damage, final double health) {
        this.delta = delta;
        this.damage = damage;
        this.health = health;
        this.counter = 0;
        this.isFrozen = false;
    }

    @Override
    public void move() {
        this.position = this.position.subtract(this.isFrozen ? this.delta*ZombieImpl.FREEZE_FACTOR : this.delta, 0);
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
        this.counter++;
        if (this.counter >= ZombieImpl.FREEZE_TIME) {
            this.warmUp();
            this.counter = 0;
        }
    }    

    @Override
    public void setPosition(Point2D position) {
        this.position = position;
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
        this.isFrozen = false;
    }
    
}
