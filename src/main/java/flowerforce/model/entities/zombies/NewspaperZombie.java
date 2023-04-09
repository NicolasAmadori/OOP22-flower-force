package flowerforce.model.entities.zombies;

import javafx.geometry.Point2D;

/**
 * This class models a newspaper zombie that extends {@link ZombieImpl}, so that
 * it can accelerate after receiving a certain damage.
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
     * @param newspaperHealth the health of its newspaper
     * @param difficulty the generic difficulty of the zombie
     * @param zombieName the name of the zombie
     */
    public NewspaperZombie(final double defaultDelta, final int damage, final int health, final Point2D position,
            final int newspaperHealth, final int difficulty, final String zombieName) {
        super(defaultDelta, damage, health + newspaperHealth, position, difficulty, zombieName);
        this.criticalHealth = health;
        this.isRunning = false;
    }

    /**
     * Override of {@link Zombie#receiveDamage(int)},
     * so that {@link NewspaperZombie} can accelerate after receiving a certain damage.
     * @param damage to receive
     */
    @Override
    public void receiveDamage(final int damage) {
        super.receiveDamage(damage);
        if (!this.isRunning && super.getHealth() <= this.criticalHealth) {
            super.changeVelocity(ACCELERATE_FACTOR);
            this.isRunning = true;
        }
    }

}
