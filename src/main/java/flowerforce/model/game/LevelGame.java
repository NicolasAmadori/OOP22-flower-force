package flowerforce.model.game;

public class LevelGame extends AbstractGameImpl {
    private int remainingZombie = 0;
    public LevelGame(final Level level, final World world) {
        super(level, world);
        this.remainingZombie = level.getTotalZombies();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean result() {
        return this.remainingZombie == 0 && this.zombies.isEmpty();
    }

    /**
     *
     * @return
     */
    @Override
    public int getProgressState() {
        return this.level.getTotalZombies() - remainingZombie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOver() {
        var end = super.isOver();
        if (end) {
            if (this.result()
                    && this.world.getPlayer().getLastUnlockedLevelId() == this.level.getLevelId()) {
                this.world.getPlayer().unlockedNextLevel();
                this.world.getPlayer().addCoins(this.level.getLevelCoins());
            }
        }
        return end || result();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void generateZombie() {
        if (remainingZombie != 0) {
            final var zombie = generateZombie.zombieGeneration();
            if (zombie.isPresent()) {
                remainingZombie--;
                this.zombies.add(zombie.get());
            }
            if (this.level.getBossId().isPresent() && remainingZombie == 0) {
                final var boss = this.generateZombie.bossGeneration();
                if (boss.isPresent()) {
                    remainingZombie--;
                    this.zombies.add(boss.get());
                }
            }
        }
    }
}
