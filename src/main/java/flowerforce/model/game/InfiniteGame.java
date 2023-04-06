package flowerforce.model.game;

/**
 * This game will spawn zombies until the player loses.
 */
public class InfiniteGame extends AbstractGame {
    private final ZombieGenerationInfiniteImpl generateZombie;
    /**
     * Constructor to instantiate an infinite game.
     * @param world an instance of the world that started the game
     */
    public InfiniteGame(final World world) {
        super(world.getPlayer().getLastUnlockedLevelId(), world);
        this.generateZombie = new ZombieGenerationInfiniteImpl(
                world.getPlayer().getLastUnlockedLevelId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOver() {
        final var end = super.isOver();
        if (end) {
            super.getWorld().getPlayer().addNewScore(super.getScore());
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
