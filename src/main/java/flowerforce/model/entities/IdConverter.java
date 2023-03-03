package flowerforce.model.entities;

/**
 * It allows converting from an ID to a type.
 */
public interface IdConverter {
    /**
     * This method allows us to know which type of plant corresponds to a certain ID.
     * @param idPlant ID of the plant for which we want to know the type
     * @return The type of plant that corresponds to the passed ID
     */
    Entity plantType(int idPlant);

    /**
     * This method allows us to know which type of zombie corresponds to a certain ID.
     * @param idZombie ID of the zombie for which we want to know the type
     * @return The type of zombie that corresponds to the passed ID
     */
    Zombie zombieType(int idZombie);
}
