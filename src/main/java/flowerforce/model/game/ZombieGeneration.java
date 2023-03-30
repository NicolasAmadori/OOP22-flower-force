package flowerforce.model.game;

import flowerforce.model.entities.Zombie;

import java.util.Optional;

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
     * If boss is present, spawns it.
     * @return the boss if it is present
     */
    Optional<Zombie> bossGeneration();

    /**
     * @return the number of spawned zombie
     */
    int getSpawnedZombie();

    /**
     * @return the number of the next horde zombie
     */
    int getNumberHordeZombie();
}