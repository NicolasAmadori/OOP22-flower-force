package flowerforce.model.entities;

/**
 * Models an entity that is able to move.
 */
public interface MovingEntity extends Entity {

    /**
     * Moves the entity forward in its direction.
     */
    void move();

    /**
     * 
     * @return the number of cells each entity moves every game loop cycle.
     */
    int getDeltaMovement();

}
