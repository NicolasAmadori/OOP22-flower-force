package flowerforce.model.game;

import java.util.Optional;

import flowerforce.model.entities.zombies.Zombie;

/**
 * Models the game zombie generation.
 */
public interface ZombieGeneration {

    /**
     * If possible, spawn a zombie.
     * @return the zombies if it has been spawned, otherwise an optional empty
     */
    Optional<Zombie> zombieGeneration();

    /**
     * @return the number of spawned zombie
     */
    int getSpawnedZombie();

    /**
     * @return the number of the next horde zombie
     */
    int getNumberHordeZombie();
}
