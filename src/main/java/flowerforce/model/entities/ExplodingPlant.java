package flowerforce.model.entities;

import java.util.Set;

/**
 * Models a plant that explodes.
 */
public interface ExplodingPlant extends Plant {

    /**
     * Damages zombies inside the radius.
     * @param zombieList the zombies to hit
     */
    void explodeOver(Set<Zombie> zombieList);

    /**
     * 
     * @return the radius of the explosion
     */
    int getRadius();

    /**
     * 
     * @return if the plant has exploded
     */
    boolean hasExploded();
}
