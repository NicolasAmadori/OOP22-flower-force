package flowerforce.model.game;

import flowerforce.model.entities.IdConverter;

import java.util.List;
import java.util.Optional;

/**
 * This is an implementation of {@link Level}.
 */
public class LevelImpl implements Level {

    private final List<IdConverter.Zombies> availableZombies;
    private final List<IdConverter.Plants> availablePlants;
    private final int coins;
    private final int nZombie;
    private final int iDLevel;
    private final Optional<IdConverter.Zombies> zombieBoss;

    /**
     *
     * @param availableZombies Zombies that can be used in this level
     * @param availablePlants Plants that can be used in this level
     * @param coins Coins that are awarded once the level is completed
     * @param nZombie Number of zombies that must be spawned in the level
     * @param iD ID of the level
     * @param zombieBoss If present, it contains the level boss
     */
    public LevelImpl(final int iD, final List<IdConverter.Zombies> availableZombies,
                     final List<IdConverter.Plants> availablePlants, final int coins,
                     final Optional<IdConverter.Zombies> zombieBoss, final int nZombie) {
        this.coins = coins;
        this.availablePlants = availablePlants;
        this.availableZombies = availableZombies;
        this.nZombie = nZombie;
        this.zombieBoss = zombieBoss;
        this.iDLevel = iD;
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
    public List<IdConverter.Plants> getPlantsId() {
        return this.availablePlants;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<IdConverter.Zombies> getZombiesId() {
        return this.availableZombies;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Integer getTotalZombies() {
        return this.nZombie;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Integer getLevelId() {
        return this.iDLevel;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Optional<IdConverter.Zombies> getBossId() {
        return this.zombieBoss;
    }
}
