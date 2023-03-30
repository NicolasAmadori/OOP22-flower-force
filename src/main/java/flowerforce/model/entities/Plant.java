package flowerforce.model.entities;

import flowerforce.model.entities.IdConverter.Plants;

/**
 * An interface modelling any kind of plants.
 */
public interface Plant extends LivingEntity {

    /**
     * 
     * @return the type of the plant
     */
    Plants getPlantType();

    int getCost();
}
