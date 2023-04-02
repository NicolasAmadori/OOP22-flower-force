package flowerforce.model.entities;

/**
 * An interface modelling any kind of plants.
 */
public interface Plant extends LivingEntity {

    int getCost();

    int getRechargeTime();
}
