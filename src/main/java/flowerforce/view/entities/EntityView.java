package flowerforce.view.entities;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public interface EntityView {

    enum GenericValues {
        /**
         * A generic low value.
         */
        LOW("Low"),
        /**
         * A generic medium value.
         */
        MEDIUM("Medium"),
        /**
         * A generic high value.
         */
        HIGH("High"),
        /**
         * A generic massive value.
         */
        MASSIVE("Massive");

        private final String name;

        private GenericValues(final String name) {
            this.name = name;
        }

        /**
         * 
         * @return the value name to show
         */
        public String getName() {
            return this.name;
        }
    }

    /**
     * 
     * @return the position to correctly draw the entity on the {@link GameEngine}
     */
    Point2D getPlacingPosition();

    /**
     * 
     * @return the entity image to display on the {@link GameEngine}
     */
    Image getImage();

    /**
     * 
     * @return the generic value of entity health.
     */
    GenericValues getResistanceValue();

}
