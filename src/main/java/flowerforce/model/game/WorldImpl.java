package flowerforce.model.game;

import java.util.Optional;

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
    private final Shop shop;

    /**
     * Generates a world.
     * @param player the player that plays the game
     */
    public WorldImpl(final Optional<Player> player) {
        this.player = player.orElse(new PlayerImpl(NEW_PLAYER_COINS, NEW_PLAYER_RECORD, NEW_PLAYER_LAST_UNLOCKED_LEVEL));
        this.shop = new ShopImpl(this.player);
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
    public Game createAdventureModeGame(final int levelId) {
        return new LevelGame(levelId, this.player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Game createSurvivalModeGame() {
        return new InfiniteGame(this.player);
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
        return YardInfo.getYardDimension();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRowsNum() {
        return YardInfo.getRowsNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getColsNum() {
        return YardInfo.getColsNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Shop getShop() {
        return this.shop;
    }
}
