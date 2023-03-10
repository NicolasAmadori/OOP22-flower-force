package flowerforce.model.entities;

/**
 * Models a in-game entity that has a health associated.
 */
public interface LivingEntity extends Entity {

    /**
     * 
     * @return the remaining health
     */
    double getHealth();

    /**
     * Called to do some damage on the entity.
     * @param damage the damage to do
     */
    void receiveDamage(double damage);

    /**
     * Called to update an entity's internal state.
     */
    void updateState();

}
