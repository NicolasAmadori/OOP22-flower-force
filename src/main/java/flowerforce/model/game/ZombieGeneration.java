package flowerforce.model.game;

import flowerforce.model.entities.IdConverter;
import flowerforce.model.entities.Zombie;

import java.util.Optional;
import java.util.Set;

public interface ZombieGeneration {
    Optional<Zombie> zombieGeneration();
}
