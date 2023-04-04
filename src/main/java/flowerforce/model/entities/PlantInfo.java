package flowerforce.model.entities;

/**
 * Models the info that must be saved about a {@link Plant} in order to identify its type in maps.
 */
public interface PlantInfo {
    
    /**
     * 
     * @return the name of the {@link Plant}
     */
    String getName();

    /**
     * 
     * @return the cost of the {@link Plant}
     */
    int getCost();

}
