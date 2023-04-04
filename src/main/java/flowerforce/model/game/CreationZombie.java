package flowerforce.model.game;

import flowerforce.model.entities.Zombie;

/**
 * Models a zombie spawn.
 */
public interface CreationZombie {
    /**
     * Used for the creation of a new zombie.
     * @param delta used to manage the probability of spawning the various zombies
     * @return the spawned zombie
     */
    Zombie creationZombie(int delta);

    /**
     * Used to increase the zombie level possible to spawn.
     */
    void increaseLevelZombieToSpawn();
}
