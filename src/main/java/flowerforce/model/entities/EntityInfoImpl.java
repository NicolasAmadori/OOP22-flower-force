package flowerforce.model.entities;

import javafx.geometry.Point2D;

/**
 * Implementation of {@link EntityInfo}.
 */
public class EntityInfoImpl implements EntityInfo {

    private final String name;
    private Point2D position;

    /**
     * Creates a new unique object with the info of a generic {@link Entity}.
     * @param name of the entity
     * @param position of the entity
     */
    public EntityInfoImpl(final String name, final Point2D position) {
        this.name = name;
        this.position = position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point2D getPosition() {
        return this.position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosition(final Point2D position) {
        this.position = position;
    }

}
