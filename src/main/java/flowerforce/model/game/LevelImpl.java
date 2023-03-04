package flowerforce.model.game;

import java.util.List;

/**
 * {@inheritDoc}.
 */
public class LevelImpl implements Level {

    private final List<Integer> avaiableZombies;
    private final List<Integer> avaiablePlants;
    private final int coins;
    private final int row;
    private final int nZombie;

    /**
     *
     * @param avaiableZombies Zombies that can be used in this level
     * @param avaiablePlants Plants that can be used in this level
     * @param coins Coins that are awarded once the level is completed
     * @param nZombie Number of zombies that must be spawned in the level
     * @param row Number of rows that can be used in the game field
     */
    public LevelImpl(final List<Integer> avaiableZombies, final List<Integer> avaiablePlants,
                     final int coins, final int nZombie, final int row) {
        this.coins = coins;
        this.avaiablePlants = avaiablePlants;
        this.avaiableZombies = avaiableZombies;
        this.row = row;
        this.nZombie = nZombie;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public int getLevelCoins() {
        return this.coins;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Integer> getPlantsId() {
        return this.avaiablePlants;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Integer> getZombiesId() {
        return this.avaiableZombies;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Integer getNumberOfRowAvaiable() {
        return this.row;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Integer getTotalZombies() {
        return this.nZombie;
    }
}
