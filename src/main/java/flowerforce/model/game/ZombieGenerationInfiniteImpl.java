package flowerforce.model.game;

import flowerforce.model.entities.Zombie;
import flowerforce.model.utilities.RenderingInformation;
import flowerforce.model.utilities.TimerImpl;

import java.util.*;

/**
 * This is an implementation of {@link ZombieGeneration}.
 */
public class ZombieGenerationInfiniteImpl implements ZombieGeneration {
    private static final double MIN_SECS_SPAWN_ZOMBIE = 5.0;
    private static final double MAX_SECS_SPAWN_ZOMBIE = 15.0;
    private static final double STANDARD_SECS_SPAWN_ZOMBIE_IN_HORDE = 1.0;
    private static final double STANDARD_SECS_DEC_TIME_ZOMBIE_SPAWN = 3.0;
    private static final int MIN_TIME_TO_SPAWN_ZOMBIE = (int)
            (MIN_SECS_SPAWN_ZOMBIE * RenderingInformation.getFramesPerSecond());
    private static final int START_TIME_TO_SPAWN_ZOMBIE = (int)
            (MAX_SECS_SPAWN_ZOMBIE * RenderingInformation.getFramesPerSecond());
    private static final int TIME_TO_SPAWN_HORDE_ZOMBIE = (int)
            (STANDARD_SECS_SPAWN_ZOMBIE_IN_HORDE * RenderingInformation.getFramesPerSecond());
    private static final int DEC_TIME_ZOMBIE = (int)
            (STANDARD_SECS_DEC_TIME_ZOMBIE_SPAWN * RenderingInformation.getFramesPerSecond());

    private static final int MAX_ZOMBIE_TO_SPAWN_HORDE = 30;
    private static final int INC_ZOMBIE_HORDE = 5;
    private static final int START_NUMBER_ZOMBIE_IN_HORDE = 5;
    private final TimerImpl zombieTimer;
    private int timeZombie = START_TIME_TO_SPAWN_ZOMBIE;
    private int hordeZombie = START_NUMBER_ZOMBIE_IN_HORDE;
    private int generatedZombie = 1;
    private int hordeGeneratedZombie = 0;
    private final CreationZombie genZombie;

    /**
     * @param levelId of the game started
     */
    public ZombieGenerationInfiniteImpl(final int levelId) {
        genZombie = new CreationZombie(Level.getZombiesId(levelId));
        zombieTimer = new TimerImpl(START_TIME_TO_SPAWN_ZOMBIE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Zombie> zombieGeneration() {
        this.zombieTimer.updateState();
        if (this.zombieTimer.isReady()) {
            if (generatedZombie % 7 == 0) {
                hordeGeneratedZombie++;
                if (hordeGeneratedZombie == hordeZombie) {
                    if (hordeZombie + INC_ZOMBIE_HORDE < MAX_ZOMBIE_TO_SPAWN_HORDE) {
                        hordeZombie += INC_ZOMBIE_HORDE;
                    }
                    if (timeZombie - DEC_TIME_ZOMBIE > MIN_TIME_TO_SPAWN_ZOMBIE) {
                        this.timeZombie -= DEC_TIME_ZOMBIE;
                    }
                    this.zombieTimer.setNumCycles(timeZombie);
                    generatedZombie = 1;
                    hordeGeneratedZombie = 0;
                    genZombie.increaseLevelZombieToSpawn();
                } else {
                    this.zombieTimer.setNumCycles(TIME_TO_SPAWN_HORDE_ZOMBIE);
                }
            } else {
                generatedZombie++;
            }
            return Optional.of(genZombie.creationZombie(hordeZombie / START_NUMBER_ZOMBIE_IN_HORDE + 1));
        }
        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSpawnedZombie() {
        return this.generatedZombie
                + this.hordeGeneratedZombie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberHordeZombie() {
        return hordeZombie + 7;
    }


}
