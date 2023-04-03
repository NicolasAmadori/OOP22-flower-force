package flowerforce.model.game;

import flowerforce.model.entities.Zombie;
import flowerforce.model.utilities.RenderingInformation;
import flowerforce.model.utilities.TimerImpl;
import javafx.geometry.Point2D;

import java.util.*;
import java.util.function.Function;

/**
 * This is an implementation of {@link ZombieGenerationLevel}.
 */
public class ZombieGenerationLevelImpl implements ZombieGenerationLevel {
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

    private static final int START_NUMBER_ZOMBIE_IN_HORDE = 10;
    private final TimerImpl zombieTimer;
    private int timeZombie = START_TIME_TO_SPAWN_ZOMBIE;
    private int hordeZombie = START_NUMBER_ZOMBIE_IN_HORDE;
    private int generatedZombie = 1;
    private int hordeGeneratedZombie = 0;
    private final Optional<Function<Point2D,Zombie>> boss;
    private final CreationZombie genZombie;

    /**
     * @param levelId of the game started
     */
    public ZombieGenerationLevelImpl(final int levelId) {
        genZombie = new CreationZombie(Level.getZombiesId(levelId));
        this.zombieTimer = new TimerImpl(timeZombie);
        this.boss = Level.getBossId(levelId);
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
                    hordeGeneratedZombie = 0;
                    genZombie.increaseLevelZombieToSpawn();
                    if (timeZombie - DEC_TIME_ZOMBIE > MIN_TIME_TO_SPAWN_ZOMBIE) {
                        this.timeZombie -= DEC_TIME_ZOMBIE;
                    }
                    this.zombieTimer.setNumCycles(timeZombie);
                    generatedZombie = 1;
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
        return this.hordeGeneratedZombie
                + this.generatedZombie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberHordeZombie() {
        return this.hordeZombie + 7;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Zombie bossGeneration() {
        return boss.get().apply(Yard.getEntityPosition(
                new Random().nextInt(Yard.getRowsNum()),
                Yard.getColsNum()
        ));
    }
}
