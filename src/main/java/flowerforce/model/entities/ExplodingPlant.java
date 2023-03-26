package flowerforce.model.entities;

import java.util.List;

/**
 * Models a plant that explodes.
 */
public interface ExplodingPlant extends Plant {

    /**
     * Damages zombies inside the radius.
     * @param zombieList the zombies to hit
     */
    void explodeOver(List<Zombie> zombieList);

    /**
     * 
     * @return the radius of the explosion
     */
    int getRadius();
}
