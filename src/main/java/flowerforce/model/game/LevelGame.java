package flowerforce.model.game;

/**
 * This game will spawn a specific number of zombie.
 * If the player defeat all this zombie, he will pass the level.
 */
public class LevelGame extends AbstractGame {
    private int remainingZombie;
    private final int id;
    private final ZombieGenerationLevel generateZombie;

    /**
     * Constructor to instantiate a level game.
     * @param levelId of the game started
     * @param shop an instance of the shop of the game
     * @param player an instance of the player
     */
    public LevelGame(final int levelId, final Shop shop, final Player player) {
        super(levelId, shop, player);
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
                && super.getPlayer().getLastUnlockedLevelId() == this.id) {
            super.getPlayer().unlockedNextLevel();
            super.getPlayer().addCoins(LevelInfo.getLevelCoins(id));
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
                final var boss = this.generateZombie.bossGeneration(LevelInfo.getBossId(id).get());
                super.addZombie(boss);
            }
        }
    }
}
