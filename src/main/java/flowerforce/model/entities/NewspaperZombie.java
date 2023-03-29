package flowerforce.model.entities;

import flowerforce.model.entities.IdConverter.Zombies;
import javafx.geometry.Point2D;

/**
 * This class models a newspaper zombie that extends {@link ZombieImpl}
 */
public class NewspaperZombie extends ZombieImpl {

    private static final int ACCELERATE_FACTOR = 3; //3x
    private final int criticalHealth;
    private boolean isRunning;

    /**
     * @param defaultDelta is the space traveled by the zombie every move update
     * @param damage given by the zombie
     * @param health of the zombie
     * @param position of the zombie
     * @param zombieType the type of zombie
     * @param newpaperHealth the health of its newspaper
     */
    public NewspaperZombie(final double defaultDelta, final int damage, final int health, final Point2D position,
            final Zombies zombieType, final int newspaperHealth) {
        super(defaultDelta, damage, health + newspaperHealth, position, zombieType);
        this.criticalHealth = health;
        this.isRunning = false;
    }

    @Override
    public void receiveDamage(final int damage) {
        super.receiveDamage(damage);
        if (!this.isRunning && super.getHealth() <= this.criticalHealth) {
            super.setDelta(super.getDeltaMovement() * ACCELERATE_FACTOR);
            this.isRunning = true;
        }
    }

}
