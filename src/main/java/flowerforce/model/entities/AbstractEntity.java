package flowerforce.model.entities;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.geometry.Point2D;

/**
 * Represents an abstract {@link Entity}.
 */
public abstract class AbstractEntity implements Entity {

    private final String entityName;
    private final EntityInfo entityInfo;
    private Point2D pos;

    /**
     * 
     * @param pos the position of the entity
     * @param entityName the name of the entity
     */
    protected AbstractEntity(final Point2D pos, final String entityName) {
        this.pos = pos;
        this.entityName = entityName;
        this.entityInfo = new EntityInfoImpl(entityName, pos);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point2D getPosition() {
        return this.pos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.entityName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification = "An EntityInfo is unique so it can't be returned as a copy"
    )
    public EntityInfo getEntityInfo() {
        return this.entityInfo;
    }

    /**
     * Called by subclasses to update entity's position.
     * @param newPos the position to update
     */
    protected void setPosition(final Point2D newPos) {
        this.pos = newPos;
        this.entityInfo.setPosition(newPos);
    }
}
