package flowerforce.model.game;

import flowerforce.model.entities.Zombie;
import flowerforce.model.utilities.RenderingInformation;
import flowerforce.model.utilities.TimerImpl;

import java.util.Optional;

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
    private int generatedZombie = 1;
    private int hordeGeneratedZombie = 0;
    private int hordeZombie;
    private final int startNumberZombieHorde;
    private final int zombieBeforeHorde;
    private boolean incrementableHorde;

    public AbstractZombieGeneration(final int levelId, final int zombieBeforeHorde,
                                    final int startNumberZombieHorde) {
        genZombie = new CreationZombie(LevelInfo.getZombiesInfo(levelId));
        this.zombieBeforeHorde = zombieBeforeHorde;
        this.zombieTimer = new TimerImpl(timeZombie);
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
            if (this.generatedZombie % zombieBeforeHorde == 0) {
                this.hordeGeneratedZombie++;
                if (this.hordeGeneratedZombie == this.hordeZombie) {
                    if (timeZombie - DEC_TIME_ZOMBIE > MIN_TIME_TO_SPAWN_ZOMBIE) {
                        this.timeZombie -= DEC_TIME_ZOMBIE;
                    }
                    this.zombieTimer.setNumCycles(timeZombie);
                    this.incrementableHorde = true;
                    this.generatedZombie = 1;
                    this.hordeGeneratedZombie = 0;
                    genZombie.increaseLevelZombieToSpawn();
                } else {
                    this.zombieTimer.setNumCycles(TIME_TO_SPAWN_HORDE_ZOMBIE);
                }
            } else {
                this.generatedZombie++;
            }
            return Optional.of(genZombie.creationZombie(this.hordeZombie / this.startNumberZombieHorde + 1));
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

    protected void increaseHordeZombie(final int value, final int maxRange) {
        if (this.incrementableHorde && this.hordeZombie + value < maxRange) {
            this.hordeZombie += value;
            this.incrementableHorde = false;
        }
    }
}
