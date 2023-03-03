package flowerforce.model.entities;

/**
 * Interface that models a Zombie.
 */
public interface Zombie extends ActiveEntity, LivingEntity {

    /**
     * This method freezes the zombie, reducing its velocity.
     */
    void freeze();

    /**
     * This method restore the original velocity of the zombie.
     */
    void warmUp();

    /** 
     * @return true if the zombie can bite, false otherwise
     */
    boolean bite();

}
