package flowerforce.model.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import flowerforce.model.utilities.RenderingInformation;
import javafx.geometry.Dimension2D;


/**
 * An implementation of the World the game's played in.
 */
public class WorldImpl implements World {

    private static final int NEW_PLAYER_COINS = 0;
    private static final int NEW_PLAYER_RECORD = 0;
    private static final int NEW_PLAYER_LAST_UNLOCKED_LEVEL = 1;

    private final Player player;
    private final List<Level> levelList;
    private final Level infiniteModeLevel;

    /**
     * Generates a world.
     * @param player the player that plays the game
     * @param levelList the list of all levels
     * @param infiniteModeLevel the level that models the infinite mode
     */
    public WorldImpl(final Optional<Player> player, final List<Level> levelList, final Level infiniteModeLevel) {
        this.player = player.orElse(new PlayerImpl(NEW_PLAYER_COINS, NEW_PLAYER_RECORD, NEW_PLAYER_LAST_UNLOCKED_LEVEL));
        this.levelList = levelList;
        this.infiniteModeLevel = infiniteModeLevel;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getPlayer() {
        return this.player;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Integer, Boolean> getLevels() {
        final Map<Integer, Boolean> levelsMap = new HashMap<>();
        IntStream.rangeClosed(1, this.player.getLastUnlockedLevelId())
            .forEach(e -> levelsMap.put(e, true));
        IntStream.rangeClosed(this.player.getLastUnlockedLevelId() + 1, this.levelList.size())
            .forEach(e -> levelsMap.put(e, false));
        return levelsMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Game createLevelGame(final int levelId) {
        final Level level = this.levelList.stream()
                                .filter(x -> x.getLevelId() == levelId)
                                .findAny()
                                .get();
        return new GameImpl(level, this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Game createInfiniteGame() {
        return new GameImpl(infiniteModeLevel, this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRenderingInformations() {
        return RenderingInformation.getFramesPerSecond();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dimension2D getYardDimension() {
        return Yard.getYardDimension();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRowsNum() {
        return Yard.getRowsNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getColsNum() {
        return Yard.getColsNum();
    }
}
