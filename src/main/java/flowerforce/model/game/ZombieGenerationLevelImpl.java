package flowerforce.model.game;

import flowerforce.model.entities.Zombie;
import javafx.geometry.Point2D;

import java.util.*;
import java.util.function.Function;

/**
 * This is an implementation of {@link ZombieGenerationLevel}.
 */
public class ZombieGenerationLevelImpl extends AbstractZombieGeneration implements ZombieGenerationLevel {
    private static final int START_NUMBER_ZOMBIE_IN_HORDE = 10;
    private static final int ZOMBIE_BEFORE_HORDE = 8;
    private final Optional<Function<Point2D, Zombie>> boss;

    /**
     * @param levelId of the game started
     */
    public ZombieGenerationLevelImpl(final int levelId) {
        super(levelId, ZOMBIE_BEFORE_HORDE, START_NUMBER_ZOMBIE_IN_HORDE);
        this.boss = LevelInfo.getBossId(levelId);
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
