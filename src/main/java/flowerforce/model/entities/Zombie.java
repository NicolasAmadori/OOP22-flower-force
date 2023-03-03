package flowerforce.model.entities;

public interface Zombie extends MovingEntity, LivingEntity{
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
