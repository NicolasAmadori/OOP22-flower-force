package flowerforce.model.game;

/**
 * This game will spawn zombies until the player loses.
 */
public class InfiniteGame extends AbstractGame {
    private final ZombieGenerationInfinite generateZombie;
    /**
     * Constructor to instantiate an infinite game.
     * @param shop an instance of the shop
     * @param player an instance of the player
     */
    public InfiniteGame(final Shop shop, final Player player) {
        super(player.getLastUnlockedLevelId(), shop, player);
        this.generateZombie = new ZombieGenerationInfinite(
                LevelInfo.getLastLevelId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOver() {
        final var end = super.isOver();
        if (end) {
            super.getPlayer().addNewScore(super.getScore());
        }
        return super.isOver();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean result() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getProgressState() {
        return this.generateZombie.getSpawnedZombie()
                / (double) this.generateZombie.getNumberHordeZombie();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void generateZombie() {
        final var zombie = this.generateZombie.zombieGeneration();
        zombie.ifPresent(super::addZombie);
    }
}
