package flowerforce.view.entities;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.util.Optional;

/**
 * This is an implementation of {@link EntityView}.
 */
public class EntityViewImpl implements EntityView {
    private static final String EMPTY_PATH = "";
    private static final int NULL_COST = 0;
    private final String pathPlaceableImage;
    private final String pathMenuImage;
    private final Point2D position;
    private final int cost;

    /**
     * @param placingPosition position of the entity in the field
     * @param pathPlaceableImage of the entity to show in the field
     * @param pathMenuImage of the entity to show in the menu
     * @param cost of the entity
     */
    public EntityViewImpl ( final Point2D placingPosition, final String pathPlaceableImage,
                           final String pathMenuImage, final int cost) {
        this.pathPlaceableImage = pathPlaceableImage;
        this.pathMenuImage = pathMenuImage;
        this.cost = cost;
        this.position = placingPosition;
    }

    /**
     * @param placingPosition position of the entity in the field
     * @param pathPlaceableImage of the entity to show in the field
     */
    public EntityViewImpl ( final Point2D placingPosition, final String pathPlaceableImage) {
        this(placingPosition, pathPlaceableImage, EMPTY_PATH, NULL_COST);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Integer> getCost() {
        return Optional.of(cost).filter(x -> x != 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Image getMenuImage() {
        return new Image(pathPlaceableImage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Image getPlacableImage() {
        return new Image(pathMenuImage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point2D getPlacingPosition() {
        return this.position;
    }
}
