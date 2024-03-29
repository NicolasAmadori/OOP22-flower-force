package flowerforce.model.game;

import javafx.geometry.Point2D;

import java.util.Random;
import java.util.function.Function;

import flowerforce.model.entities.zombies.Zombie;

/**
 * This is an implementation of {@link ZombieGenerationLevel}.
 */
public class ZombieGenerationLevelImpl extends AbstractZombieGeneration implements ZombieGenerationLevel {
    private static final int START_NUMBER_ZOMBIE_IN_HORDE = 10;
    private static final int ZOMBIE_BEFORE_HORDE = 7;

    private final Random rand = new Random();

    /**
     * @param levelId of the game started
     */
    public ZombieGenerationLevelImpl(final int levelId) {
        super(levelId, ZOMBIE_BEFORE_HORDE, START_NUMBER_ZOMBIE_IN_HORDE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Zombie bossGeneration(final Function<Point2D, Zombie> boss) {
        return boss.apply(YardInfo.getEntityPosition(
                this.rand.nextInt(YardInfo.getRowsNum()),
                YardInfo.getColsNum()
        ));
    }
}
