package flowerforce.model.game;

import flowerforce.model.entities.Zombie;

import java.util.*;

/**
 * This is an implementation of {@link ZombieGeneration}.
 */
public class ZombieGenerationInfiniteImpl extends AbstractZombieGeneration {

    private static final int ZOMBIE_BEFORE_HORDE = 7;
    private static final int MAX_ZOMBIE_TO_SPAWN_HORDE = 30;
    private static final int INC_ZOMBIE_HORDE = 5;
    private static final int START_NUMBER_ZOMBIE_IN_HORDE = 5;

    /**
     * @param levelId of the game started
     */
    public ZombieGenerationInfiniteImpl(final int levelId) {
        super(levelId, ZOMBIE_BEFORE_HORDE, START_NUMBER_ZOMBIE_IN_HORDE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Zombie> zombieGeneration() {
        super.increaseHordeZombie(INC_ZOMBIE_HORDE, MAX_ZOMBIE_TO_SPAWN_HORDE);
        return super.zombieGeneration();
    }
}
