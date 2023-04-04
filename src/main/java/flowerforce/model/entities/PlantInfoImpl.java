package flowerforce.model.entities;

/**
 * Implementation of {@link PlantInfo}.
 */
public class PlantInfoImpl implements PlantInfo {

    private final String name;
    private final int cost;

    /**
     * Creates a new unique object with the info of a generic {@link Plant}.
     * @param name of the plant
     * @param cost of the plant
     */
    public PlantInfoImpl(final String name, final int cost) {
        this.name = name;
        this.cost = cost;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCost() {
        return this.cost;
    }

}
