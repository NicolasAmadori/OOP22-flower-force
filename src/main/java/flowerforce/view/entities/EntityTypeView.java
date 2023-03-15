package flowerforce.view.entities;

import javafx.scene.image.Image;

public interface EntityTypeView {

    enum GenericValues {
        /**
         * A generic null value.
         */
        NONE("None"),
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
     * @return the entity image to display on the {@link GameEngine}
     */
    Image getImage();

    /**
     * 
     * @return the generic value of entity health.
     */
    GenericValues getResistanceValue();

}
