package flowerforce.model.entities;

/**
 * Models a zombie that is both moving and living entity.
 */
public interface Zombie extends MovingEntity, LivingEntity {

    /**
     * This method slows down the zombie.
     */
    void freeze();

    /**
     * This method restore the original velocity of the zombie.
     */
    void warmUp();

    /**
     * 
     * @param plant that can be eaten by the zombie
     */
    void manageEating(Plant plant);

    /**
     * 
     * @return the type of zombie
     */
    IdConverter.Zombies getZombieType();
}
