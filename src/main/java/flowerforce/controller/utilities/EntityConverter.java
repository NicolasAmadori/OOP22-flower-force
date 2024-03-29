package flowerforce.controller.utilities;

import flowerforce.model.entities.EntityInfo;
import flowerforce.view.entities.EntityView;
import javafx.geometry.Point2D;

/**
 * The Entity converter to create entities to draw in the view.
 */
public interface EntityConverter {

    /**
     * Create an entityView using the plant information.
     * @param plantInfo the information of the plant
     * @return The entityView representing the plant
     */
    EntityView getPlantView(EntityInfo plantInfo);

    /**
     * Create an entityView using the zombie information.
     * @param zombieInfo the information of the zombie
     * @return The entityView representing the zombie
     */
    EntityView getZombieView(EntityInfo zombieInfo);

    /**
     * Create an entityView using the bullet information.
     * @param bulletInfo the information of the bullet
     * @return The entityView representing the bullet
     */
    EntityView getBulletView(EntityInfo bulletInfo);

    /**
     * Change the position of a Zombie EntityView.
     * @param entityView the EntityView instance whose position is to change
     * @param newPosition the new position to set
     */
    void changeZombieViewPosition(EntityView entityView, Point2D newPosition);

    /**
     * Change the position of a Bullet EntityView.
     * @param entityView the EntityView instance whose position is to change
     * @param newPosition the new position to set
     */
    void changeBulletViewPosition(EntityView entityView, Point2D newPosition);
}
