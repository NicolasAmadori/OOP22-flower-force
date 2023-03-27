package flowerforce.model.game;

import flowerforce.model.entities.Zombie;

import java.util.Optional;

public interface ZombieGeneration {
    Optional<Zombie> zombieGeneration();
    Optional<Zombie> bossGeneration();
}
