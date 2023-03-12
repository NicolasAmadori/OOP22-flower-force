package flowerforce.view.entities;

import java.util.Optional;

public interface PlantView extends EntityTypeView {

    /**
     * 
     * @return a generic value of plant damage if it has one, an empty {@link Optional} otherwise
     */
    Optional<GenericValues> getDamageValue();
    
    /**
     * 
     * @return the cost in suns of the plant to show in the view.
     */
    int getCost();
}
