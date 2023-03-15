package flowerforce.view.entities;

import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;

import java.util.Optional;

/**
 * This is an implementation of {@link EntityView}
 */
public class EntityViewImpl implements EntityView {
    private final String pathImage;
    private final Point2D position;
    private final Optional<Integer> cost;

    /**
     * @param placingPosition position of the entity in the field
     * @param pathImage of the entity to show
     * @param cost of the entity
     */
    public EntityViewImpl(final Point2D placingPosition, final String pathImage, final int cost) {
        this.pathImage = pathImage;
        this.cost = Optional.of(cost);
        this.position = placingPosition;
    }

    /**
     * @param placingPosition position of the entity in the field
     * @param pathImage of the entity to show
     */
    public EntityViewImpl(final Point2D placingPosition, final String pathImage) {
        this.pathImage = pathImage;
        this.cost = Optional.empty();
        this.position = placingPosition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Integer> getCost() {
        return this.cost;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImageView getImageView() {
        return new ImageView(pathImage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point2D getPlacingPosition() {
        return this.position;
    }
    
}
