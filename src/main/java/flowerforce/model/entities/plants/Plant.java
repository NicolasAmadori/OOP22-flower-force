package flowerforce.model.entities.plants;

import flowerforce.model.entities.LivingEntity;

/**
 * An interface modelling any kind of plants.
 */
public interface Plant extends LivingEntity {

    /**
     *
     * @return the plant's cost
     */
    int getCost();

    /**
     * 
     * @return Plant's recharge time (i.e. time until it will be avaliable again
     * after being placed in-game)
     */
    int getRechargeTime();
}
