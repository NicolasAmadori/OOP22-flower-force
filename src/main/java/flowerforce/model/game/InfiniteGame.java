package flowerforce.model.game;

/**
 * An implementation of a new infinite game.
 * This game will spawn zombies until the player loses.
 */
public class InfiniteGame extends AbstractGameImpl {
    private final ZombieGenerationInfiniteImpl generateZombie;

    /**
     * Constructor to instantiate an infinite game.
     * @param levelId of the game started
     * @param world an instance of the world that started the game
     */
    public InfiniteGame(final int levelId, final World world) {
        super(levelId, world);
        this.generateZombie = new ZombieGenerationInfiniteImpl(levelId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOver() {
        final var end = super.isOver();
        if (end) {
            this.getWorld().getPlayer().setNewScoreRecord(this.getScore());
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
     *
     * @return
     */
    @Override
    public int getProgressState() {
        return this.generateZombie.getSpawnedZombie()
                / this.generateZombie.getNumberHordeZombie() * 100;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void generateZombie() {
        final var zombie = this.generateZombie.zombieGeneration();
        zombie.ifPresent(this::addZombie);
    }
}
