package flowerforce.view.entities;

import javafx.geometry.Point2D;

public class EntityViewImpl implements EntityView {

    private final EntityTypeView entityType;
    private final Point2D position;

    public EntityViewImpl(final EntityTypeView entityType, final Point2D placingPosition) {
        this.entityType = entityType;
        this.position = placingPosition;
    }

    @Override
    public EntityTypeView getEntityType() {
        return this.entityType;
    }

    @Override
    public Point2D getPlacingPosition() {
        return this.position;
    }
    
}
