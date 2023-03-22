package flowerforce.view.entities;

import javafx.scene.image.Image;

import java.util.Optional;

public interface CardView {

    /**
     * @return the image of the entity to show in the menu
     */
    Image getMenuImage();

    /**
     * @return if it's present, return the cost of entity
     */
    int getCost();
}
