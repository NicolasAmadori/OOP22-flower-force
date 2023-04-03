package flowerforce.model.game;

import java.util.Optional;

/**
 * An implementation of a new level game.
 * This game will spawn a specific number of zombie.
 * If the player defeat all this zombie, he will pass the level.
 */
public class LevelGame extends AbstractGameImpl {
    private int remainingZombie;
    private final int id;
    private final ZombieGenerationLevel generateZombie;
    /**
     * Constructor to instantiate a level game.
     * @param levelId of the game started
     * @param world an instance of the world that started the game
     */
    public LevelGame(final int levelId, final World world) {
        super(levelId, world);
        this.id = levelId;
        this.remainingZombie = Level.getTotalZombies(levelId);
        generateZombie = new ZombieGenerationLevelImpl(levelId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean result() {
        return this.remainingZombie == 0 && this.getPlacedZombies().isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getProgressState() {
        return (Level.getTotalZombies(this.id) - remainingZombie) / (double) Level.getTotalZombies(this.id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOver() {
        final var end = super.isOver();
        if (end && this.result() &&
                this.getWorld().getPlayer().getLastUnlockedLevelId() == this.id) {
            this.getWorld().getPlayer().unlockedNextLevel();
            this.getWorld().getPlayer().addCoins(Level.getLevelCoins(id));
        }
        return end || result();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void generateZombie() {
        if (remainingZombie != 0) {
            final var zombie = this.generateZombie.zombieGeneration();
            if (zombie.isPresent()) {
                remainingZombie--;
                this.addZombie(zombie.get());
            }
            if (Level.getBossId(id).isPresent() && remainingZombie == 0) {
                final var boss = this.generateZombie.bossGeneration();
                this.addZombie(boss);
            }
        }
    }
}
