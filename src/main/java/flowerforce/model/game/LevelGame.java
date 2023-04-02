package flowerforce.model.game;

/**
 * An implementation of a new level game.
 * This game will spawn a specific number of zombie.
 * If the player defeat all this zombie, he will pass the level.
 */
public class LevelGame extends AbstractGameImpl {
    private int remainingZombie;

    private final ZombieGenerationLevel generateZombie;
    /**
     * Constructor to instantiate a level game.
     * @param level of the game started
     * @param world an instance of the world that started the game
     */
    public LevelGame(final int levelId, final World world) {
        super(level, world);
        this.remainingZombie = level.getTotalZombies();
        generateZombie = new ZombieGenerationLevelImpl(level);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean result() {
        return this.remainingZombie == 0 && this.getZombies().isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getProgressState() {
        return (this.getLevel().getTotalZombies() - remainingZombie) / this.getLevel().getTotalZombies() * 100;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOver() {
        final var end = super.isOver();
        if (end && this.result() &&
                this.getWorld().getPlayer().getLastUnlockedLevelId() == this.getLevel().getLevelId()) {
            this.getWorld().getPlayer().unlockedNextLevel();
            this.getWorld().getPlayer().addCoins(this.getLevel().getLevelCoins());
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
            if (this.getLevel().getBossId().isPresent() && remainingZombie == 0) {
                final var boss = this.generateZombie.bossGeneration();
                this.addZombie(boss);
            }
        }
    }
}
