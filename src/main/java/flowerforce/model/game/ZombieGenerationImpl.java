package flowerforce.model.game;

import flowerforce.model.entities.IdConverter;
import flowerforce.model.entities.Zombie;
import flowerforce.model.utilities.TimerImpl;

import java.util.*;
import java.util.stream.Collectors;

public class ZombieGenerationImpl implements ZombieGeneration {
    private static final int MAX_ZOMBIE_TO_SPAWN_HORDE = 30;
    private static final int INC_ZOMBIE_HORDE = 5;
    private static final int DEC_TIME_ZOMBIE = 50;
    private static final int MIN_TIME_TO_SPAWN_ZOMBIE = 150;
    private static final int TIME_TO_SPAWN_HORDE_ZOMBIE = 30;
    private static final int START_TIME_TO_SPAWN_ZOMBIE = 300;
    private static final int START_NUMBER_ZOMBIE_IN_HORDE = 300;
    private final TimerImpl zombieTimer;
    private int timeZombie = START_TIME_TO_SPAWN_ZOMBIE;
    private int hordeZombie = START_NUMBER_ZOMBIE_IN_HORDE;
    private int generatedZombie = 1;
    private int hordeGeneratedZombie = 0;
    private final int zombieMaxDifficulty;
    private final List<IdConverter.Zombies> zombies;
    private final Optional<IdConverter.Zombies> boss;
    private int levelZombieToSpawn;
    private int prevRow = -1;
    public ZombieGenerationImpl(final Level level) {
        this.zombieTimer = new TimerImpl(timeZombie);
        this.zombies = level.getZombiesId();
        this.boss = level.getBossId();
        this.zombieMaxDifficulty = zombies.stream()
                .mapToInt(IdConverter.Zombies::getDifficulty)
                .max()
                .orElse(0);
        this.levelZombieToSpawn = zombies.stream()
                .mapToInt(IdConverter.Zombies::getDifficulty)
                .min()
                .orElse(0);
    }

    @Override
    public Optional<Zombie> zombieGeneration() {
        this.zombieTimer.updateState();
        if (this.zombieTimer.isReady()) {
            if (generatedZombie % 10 == 0) {
                hordeGeneratedZombie++;
                if (hordeGeneratedZombie == hordeZombie) {
                    hordeGeneratedZombie = 0;
                    if (levelZombieToSpawn < zombieMaxDifficulty) {
                        levelZombieToSpawn++;
                    }
                    if (hordeZombie + INC_ZOMBIE_HORDE < MAX_ZOMBIE_TO_SPAWN_HORDE) {
                        hordeZombie += INC_ZOMBIE_HORDE;
                    }
                    if (timeZombie - DEC_TIME_ZOMBIE > MIN_TIME_TO_SPAWN_ZOMBIE) {
                        this.timeZombie -= DEC_TIME_ZOMBIE;
                    }
                    this.zombieTimer.setNumCycles(timeZombie);
                    generatedZombie++;
                } else {
                    this.zombieTimer.setNumCycles(TIME_TO_SPAWN_HORDE_ZOMBIE);
                }
            } else {
                generatedZombie++;
            }
            return Optional.of(this.creationZombie());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Zombie> bossGeneration() {
        if (zombieTimer.isReady() && boss.isPresent()) {
            return Optional.of(IdConverter.createZombie(this.boss.get(),
                    Yard.getEntityPosition(
                            new Random().nextInt(Yard.getRowsNum()),
                            Yard.getColsNum() - 1
                    )));
        }
        return Optional.empty();
    }

    private Zombie creationZombie() {
        final var zombieToSpawn = zombies.stream()
                .filter(zombie -> zombie.getDifficulty() <= levelZombieToSpawn)
                .collect(Collectors.toSet());

        int randomValue = new Random().nextInt(zombieToSpawn.stream()
                .mapToInt(IdConverter.Zombies::getDifficulty)
                .sum() + 1);

        int row;
        do {
            row = new Random().nextInt(Yard.getRowsNum());
        } while (row == prevRow);
        prevRow = row;

        for (final var zombie : zombieToSpawn) {
            randomValue = randomValue - (zombieMaxDifficulty + 1 - zombie.getDifficulty());
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
}
