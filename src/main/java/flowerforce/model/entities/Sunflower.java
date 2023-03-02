package flowerforce.model.entities;

/**
 * Models a plant that doesn't shoot bullets and produce suns.
 */
public interface Sunflower extends LivingEntity{

    /**
     * 
     * @return true if a sun has been produced
     */
    boolean isSunGenerated();
}
