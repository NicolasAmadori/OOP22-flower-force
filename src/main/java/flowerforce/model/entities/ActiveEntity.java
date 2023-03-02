package flowerforce.model.entities;

/**
 * Models an entity that is able to move.
 */
public interface ActiveEntity extends Entity {

    /**
     * Moves the entity forward in its direction.
     */
    void move();

    /**
     * 
     * @return the damage that one entity does to an enemy entity
     */
    double getDamage();

}
