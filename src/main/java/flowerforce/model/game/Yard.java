package flowerforce.model.game;

import java.util.Map;

import flowerforce.model.entities.Bullet;
import flowerforce.model.entities.Entity;
import flowerforce.model.entities.Plant;
import flowerforce.model.entities.Zombie;

/**
 * Models the yard of the game, that controls collisions between entities.
 */
public interface Yard {

    /**
     * @param entity to be placed based on its position
     */
    void placeEntity(Entity entity);

    /** 
     * @return a map of the bullets that are colliding associated whith their respective zombies
     */
    Map<Bullet, Zombie> collidingBullets();

    /** 
     * @return a map of the zombies that are eating associated with their respective plants
     */
    Map<Zombie, Plant> eatingZombies();

}
