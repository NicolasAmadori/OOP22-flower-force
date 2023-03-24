package flowerforce.model.game;

import flowerforce.common.TimerImpl;
import flowerforce.model.entities.IdConverter;
import flowerforce.model.entities.Zombie;
import flowerforce.model.entities.ZombieImpl;

import java.util.*;
import java.util.stream.Collectors;

public class ZombieGenerationImpl implements ZombieGeneration {
    private static final int HORDE_MAX_ZOMBIE_TO_SPAWN = 30;
    private static final int INC_ZOMBIE = 5;
    private static final int DEC_TIME_ZOMBIE = 50;
    private static final int MIN_TIME_TO_FOR_SPAWN_ZOMBIE = 150;
    private static final int timeHordeZombie = 30;
    private final TimerImpl zombieTimer;
    private int timeZombie = 300;
    private int hordeZombie = 5;
    private int generatedZombie = 1;
    private int hordeGeneratedZombie = 0;
    private final int zombieMaxDifficulty;
    private final List<IdConverter.Zombies> zombies;
    private int levelZombieToSpawn;
    private int prevRow = -1;
    public ZombieGenerationImpl(final List<IdConverter.Zombies> zombies) {
        this.zombieTimer = new TimerImpl(timeZombie);
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
    @Override
    public Optional<Zombie> zombieGeneration() {
        this.zombieTimer.updateState();
        if(this.zombieTimer.isReady()) {
            if(generatedZombie % 10 == 0) {
                hordeGeneratedZombie++;
                if(hordeGeneratedZombie == hordeZombie) {
                    if(levelZombieToSpawn < zombieMaxDifficulty) {
                        levelZombieToSpawn++;
                    }
                    if(hordeZombie + INC_ZOMBIE < HORDE_MAX_ZOMBIE_TO_SPAWN) {
                        hordeZombie += INC_ZOMBIE;
                    }
                    if(timeZombie - DEC_TIME_ZOMBIE > MIN_TIME_TO_FOR_SPAWN_ZOMBIE) {
                        this.timeZombie -= DEC_TIME_ZOMBIE;
                    }
                    this.zombieTimer.setNumCycles(timeZombie);
                    generatedZombie++;
                }
                else {
                    this.zombieTimer.setNumCycles(timeHordeZombie);
                }
            } else {
                generatedZombie++;
            }
            return Optional.of(this.creationZombie());
        }
        return Optional.empty();
    }

    private Zombie creationZombie() {
        var zombieToSpawn = zombies.stream()
                .filter(zombie -> zombie.getDifficulty() <= levelZombieToSpawn)
                .collect(Collectors.toSet());

        int randomValue = new Random().nextInt(zombieToSpawn.stream()
                .mapToInt(IdConverter.Zombies::getDifficulty)
                .sum() + 1);

        int row;
        do {
            row = new Random().nextInt(Yard.getRowsNum());
        }while (row == prevRow);
        prevRow = row;

        for (var zombie : zombieToSpawn) {
            randomValue = randomValue - (zombieMaxDifficulty + 1 - zombie.getDifficulty());
            if (randomValue <= 0) {
                return IdConverter.createZombie(zombie,
                        Yard.getEntityPosition(
                                row,
                                Yard.getColsNum()
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
