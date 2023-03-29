package flowerforce.model.game;

public class InfiniteGame extends AbstractGameImpl {
    public InfiniteGame(final Level level, final World world) {
        super(level, world);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOver() {
        var end = super.isOver();
        if (end) {
            this.world.getPlayer().setNewScoreRecord(this.score);
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
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void generateZombie() {
        final var zombie = this.generateZombie.zombieGeneration();
        zombie.ifPresent(z -> this.zombies.add(z));
    }
}
