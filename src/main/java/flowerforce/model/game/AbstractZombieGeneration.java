package flowerforce.model.game;

import flowerforce.model.entities.zombies.Zombie;
import flowerforce.model.utilities.RenderingInformation;
import flowerforce.model.utilities.TimerImpl;

import java.util.Optional;

/**
 * This is an implementation of {@link ZombieGeneration}.
 */
public abstract class AbstractZombieGeneration implements ZombieGeneration {

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

    private final TimerImpl zombieTimer;
    private int timeZombie = START_TIME_TO_SPAWN_ZOMBIE;
    private final CreationZombie genZombie;
    private int generatedZombie;
    private int hordeGeneratedZombie;
    private int hordeZombie;
    private final int startNumberZombieHorde;
    private final int zombieBeforeHorde;
    private boolean incrementableHorde;

    /**
     * @param levelId of the level started
     * @param zombieBeforeHorde number of zombies to spawn before the horde
     * @param startNumberZombieHorde number of zombies in the starting horde
     */
    public AbstractZombieGeneration(final int levelId, final int zombieBeforeHorde,
                                    final int startNumberZombieHorde) {
        genZombie = new CreationZombieImpl(LevelInfo.getZombiesInfo(levelId));
        this.zombieBeforeHorde = zombieBeforeHorde;
        this.zombieTimer = new TimerImpl(this.timeZombie);
        this.startNumberZombieHorde = startNumberZombieHorde;
        this.hordeZombie = startNumberZombieHorde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Zombie> zombieGeneration() {
        this.zombieTimer.updateState();
        if (this.zombieTimer.isReady()) {
            if (this.generatedZombie == this.zombieBeforeHorde) {
                this.hordeGeneratedZombie++;
                if (this.hordeGeneratedZombie == this.hordeZombie) {
                    if (timeZombie - DEC_TIME_ZOMBIE > MIN_TIME_TO_SPAWN_ZOMBIE) {
                        this.timeZombie -= DEC_TIME_ZOMBIE;
                    }
                    this.zombieTimer.setNumCycles(this.timeZombie);
                    this.incrementableHorde = true;
                    this.generatedZombie = 0;
                    this.hordeGeneratedZombie = 0;
                    this.genZombie.increaseLevelZombieToSpawn();
                } else {
                    this.zombieTimer.setNumCycles(TIME_TO_SPAWN_HORDE_ZOMBIE);
                }
            } else {
                this.generatedZombie++;
            }
            return Optional.of(this.genZombie.creationZombie(this.hordeZombie / this.startNumberZombieHorde));
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
        return this.hordeZombie + this.zombieBeforeHorde;
    }

    /**
     * called if you want to increase the number of zombies in the horde.
     * @param value used to increase the number of zombies in the horde
     * @param maxRange used to check that the number of zombies in the
     *                 horde does not exceed a certain value
     */
    protected void increaseHordeZombie(final int value, final int maxRange) {
        if (this.incrementableHorde && this.hordeZombie + value < maxRange) {
            this.hordeZombie += value;
            this.incrementableHorde = false;
        }
    }
}
