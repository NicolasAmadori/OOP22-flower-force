package flowerforce.model.game;

import java.util.HashSet;
import java.util.Set;

/**
 * This is an implementation of {@link Player}.
 */
public class PlayerImpl implements Player {

    private int nCoins;
    private int scoreRecord;
    private int lastUnlockedLevelId;
    private Set<Integer> plantsIds = new HashSet<>();

    /**
     * Constructor to instantiate a totally new player.
     */
    public PlayerImpl() {
        this(0, 0, 1);
    }

    /**
     * Constructor to instantiate an existing player, with give values.
     * @param nCoins The integer representing the number of coins the player has
     * @param scoreRecord The integer representing the score record of the player
     * @param lastUnlockedLevelId The integer representing the id of the last level the player has unlocked
     */
    public PlayerImpl(final int nCoins, final int scoreRecord, final int lastUnlockedLevelId) {
        this.nCoins = nCoins;
        this.scoreRecord = scoreRecord;
        this.lastUnlockedLevelId = lastUnlockedLevelId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCoins() {
        return nCoins;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addCoins(final int nCoins) {
        this.nCoins += nCoins;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean subtractCoins(final int nCoins) {
        if (nCoins > this.nCoins) {
            return false;
        }
        this.nCoins -= nCoins;
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getScoreRecord() {
        return this.scoreRecord;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addNewScore(final int score) {
        if (score > this.scoreRecord) {
            this.scoreRecord = score;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLastUnlockedLevelId() {
        return this.lastUnlockedLevelId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unlockedNextLevel() {
        this.lastUnlockedLevelId++;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPlant(final int plantIndex) {
        this.plantsIds.add(plantIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Integer> getPlantsIds() {
        return this.plantsIds;
    }
}
