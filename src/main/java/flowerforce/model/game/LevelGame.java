package flowerforce.model.game;

/**
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
        this.remainingZombie = LevelInfo.getTotalZombies(levelId);
        generateZombie = new ZombieGenerationLevelImpl(levelId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean result() {
        return this.remainingZombie == 0 && super.getPlacedZombies().isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getProgressState() {
        return (LevelInfo.getTotalZombies(this.id) - remainingZombie) / (double) LevelInfo.getTotalZombies(this.id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOver() {
        final var end = super.isOver();
        if (end && this.result()
                && super.getWorld().getPlayer().getLastUnlockedLevelId() == this.id) {
            super.getWorld().getPlayer().unlockedNextLevel();
            super.getWorld().getPlayer().addCoins(LevelInfo.getLevelCoins(id));
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
                super.addZombie(zombie.get());
            }
            if (LevelInfo.getBossId(id).isPresent() && remainingZombie == 0) {
                final var boss = this.generateZombie.bossGeneration();
                super.addZombie(boss);
            }
        }
    }
}
