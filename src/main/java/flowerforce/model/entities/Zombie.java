package flowerforce.model.entities;

/**
 * Models a zombie that is both {@link MovingEntity} and {@link LivingEntity}.
 */
public interface Zombie extends MovingEntity, LivingEntity {

    /**
     * Slows down the zombie.
     */
    void freeze();

    /**
     * Restore the original velocity of the zombie.
     */
    void warmUp();

    /**
     * 
     * @param plant that can be eaten by the zombie
     * @return true if the zombie has bitten the plant, false otherwise
     */
    boolean manageEating(Plant plant);

    /**
     * 
     * @return the generic difficulty of the zombie
     */
    int getDifficulty();
}
