package flowerforce.view.entities;

import javafx.scene.image.Image;

import java.util.Optional;

/**
 * This is an implementation of {@link EntityView}.
 */
public class CardViewImpl implements CardView {
    private final int cost;
    private final String pathMenuImage;

    /**
     * @param cost of the plant to place
     * @param pathMenuImage of the entity to show in the menu
     */
    public CardViewImpl(final int cost, final String pathMenuImage) {
        this.pathMenuImage = pathMenuImage;
        this.cost = cost;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCost() {
        return cost;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Image getMenuImage() {
        return new Image(pathMenuImage);
    }
}
