package flowerforce.model.game;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * This is an implementation of {@link Player}.
 */
public class PlayerImpl implements Player {

    private int nCoins;
    private int scoreRecord;
    private int lastUnlockedLevelId;
    private final Set<Integer> plantsIds;

    /**
     * Constructor to instantiate a totally new player.
     */
    public PlayerImpl() {
        this(0, 0, 1);
    }

    /**
     * Constructor to instantiate an existing player (probably loaded from a saving file), with given values.
     * @param nCoins The integer representing the number of coins the player has
     * @param scoreRecord The integer representing the score record of the player
     * @param lastUnlockedLevelId The integer representing the id of the last level the player has unlocked
     */
    public PlayerImpl(final int nCoins, final int scoreRecord, final int lastUnlockedLevelId) {
        this(nCoins, scoreRecord, lastUnlockedLevelId, Optional.empty());
    }

    /**
     * Instantiate a copy of an existing Player instance.
     * @param p The player instance to copy
     */
    public PlayerImpl(final Player p) {
        this(p.getCoins(), p.getScoreRecord(), p.getLastUnlockedLevelId(), Optional.of(p.getPlantsIds()));
    }

    //Constructor that get all the private information to create a copy of an instance.
    private PlayerImpl(final int nCoins, final int scoreRecord,
                       final int lastUnlockedLevelId, final Optional<Set<Integer>> plantsIds) {
        this.nCoins = nCoins;
        this.scoreRecord = scoreRecord;
        this.lastUnlockedLevelId = lastUnlockedLevelId;
        this.plantsIds = plantsIds.isPresent() ? plantsIds.get() : new HashSet<>();
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
        if (nCoins < 0) {
            throw new IllegalArgumentException("nCoins must be a positive number.");
        }
        this.nCoins += nCoins;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean subtractCoins(final int nCoins) {
        if (nCoins < 0) {
            throw new IllegalArgumentException("nCoins must be a positive number.");
        }
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
        if (score < 0) {
            throw new IllegalArgumentException("score must be a positive number.");
        }
        this.scoreRecord = Math.max(this.scoreRecord, score);
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
        if (plantIndex < 0) {
            throw new IllegalArgumentException("plantIndex must be a positive number.");
        }
        this.plantsIds.add(plantIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Integer> getPlantsIds() {
        return Set.copyOf(this.plantsIds);
    }
}
