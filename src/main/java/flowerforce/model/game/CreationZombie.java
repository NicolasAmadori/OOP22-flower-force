package flowerforce.model.game;

import flowerforce.model.entities.IdConverter;
import flowerforce.model.entities.Zombie;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class CreationZombie {
    private final int zombieMaxDifficulty;
    private final List<IdConverter.Zombies> zombies;
    private int levelZombieToSpawn;
    private int prevRow = -1;

    /**
     * @param zombies the list of zombie type to spawn
     */
    public CreationZombie(final List<IdConverter.Zombies> zombies) {
        this.zombies = zombies;
        this.zombieMaxDifficulty = zombies.stream()
                .mapToInt(IdConverter.Zombies::getDifficulty)
                .max()
                .orElse(0);
        this.levelZombieToSpawn = zombies.stream()
                .mapToInt(IdConverter.Zombies::getDifficulty)
                .min()
                .orElse(0);
    }

    /**
     * Used for the creation of a new zombie.
     * @return the spawned zombie
     */
    public Zombie creationZombie(final int delta) {
        final var zombieToSpawn = zombies.stream()
                .filter(zombie -> zombie.getDifficulty() <= levelZombieToSpawn)
                .collect(Collectors.toSet());

        int randomValue = new Random().nextInt(zombieToSpawn.stream()
                .mapToInt(z -> zombieMaxDifficulty - z.getDifficulty() + delta)
                .sum() + 1);

        int row;
        do {
            row = new Random().nextInt(Yard.getRowsNum());
        } while (row == prevRow);
        prevRow = row;

        for (final var zombie : zombieToSpawn) {
            randomValue = randomValue - (zombieMaxDifficulty + delta - zombie.getDifficulty());
            if (randomValue <= 0) {
                return IdConverter.createZombie(zombie,
                        Yard.getEntityPosition(
                                row,
                                Yard.getColsNum() - 1
                        ));
            }
        }
        return IdConverter.createZombie(IdConverter.Zombies.BASIC,
                Yard.getEntityPosition(
                        row,
                        Yard.getColsNum()
                ));
    }

    /**
     * Used to increase the zombie level possible to spawn
     */
    public void increaseLevelZombieToSpawn() {
        if (levelZombieToSpawn < zombieMaxDifficulty) {
            levelZombieToSpawn += levelZombieToSpawn;
        }
    }
}
