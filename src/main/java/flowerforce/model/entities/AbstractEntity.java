package flowerforce.model.entities;

import flowerforce.common.Timer;

/**
 * Represents a generical entity.
 */
public abstract class AbstractEntity implements Entity {

    private final Timer timer;

    /**
     * 
     * @param timer the timer used by the entity to do actions
     */
    protected AbstractEntity(final Timer timer) {
        this.timer = timer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        this.timer.updateState();
    }

}
