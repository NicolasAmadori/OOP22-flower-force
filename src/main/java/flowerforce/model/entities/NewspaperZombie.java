package flowerforce.model.entities;

import flowerforce.model.entities.IdConverter.Zombies;
import javafx.geometry.Point2D;

/**
 * This class models a newspaper zombie that extends {@link ZombieImpl}
 */
public class NewspaperZombie extends ZombieImpl {

    private static final int ACCELERATE_FACTOR = 4; //4x
    private final int newspaperHealth;
    private final double initialHealth;
    private boolean isRunning;

    /**
     * @param defaultDelta is the space traveled by the zombie every move update
     * @param damage given by the zombie
     * @param health of the zombie
     * @param position of the zombie
     * @param zombieType the type of zombie
     * @param newpaperHealth the health of its newspaper
     */
    public NewspaperZombie(final int defaultDelta, final double damage, final double health, final Point2D position,
            final Zombies zombieType, final int newspaperHealth) {
        super(defaultDelta, damage, health, position, zombieType);
        this.newspaperHealth = newspaperHealth;
        this.initialHealth = health;
        this.isRunning = false;
    }

    @Override
    public void receiveDamage(final double damage) {
        super.receiveDamage(damage);
        if (!this.isRunning && super.getHealth() <= this.initialHealth - this.newspaperHealth) {
            super.setDelta(super.getDeltaMovement() * ACCELERATE_FACTOR);
            this.isRunning = true;
        }
    }

}
