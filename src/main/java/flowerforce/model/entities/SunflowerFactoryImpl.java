package flowerforce.model.entities;

import flowerforce.model.entities.IdConverter.Plants;
import flowerforce.model.utilities.RenderingInformation;
import flowerforce.model.utilities.TimerImpl;
import javafx.geometry.Point2D;

public class SunflowerFactoryImpl implements SunflowerFactory {

    private static final double STANDARD_SECS_SUN_PRODUCING_TIME = 24.25;
    private static final int STANDARD_SUNPRODUCING_TIME = RenderingInformation.convertSecondsToCycles(STANDARD_SECS_SUN_PRODUCING_TIME);
    private static final int STANDARD_SUNFLOWER_HEALTH = 300;
    private static final int COMMON_SUNFLOWER_COST = 50;
    private static final int FAST_SUNFLOWER_COST = 150;

    @Override
    public Sunflower createCommonSunflower(final Point2D pos, final Plants plantType) {
        return new SunflowerImpl(pos, new TimerImpl(STANDARD_SUNPRODUCING_TIME), STANDARD_SUNFLOWER_HEALTH, COMMON_SUNFLOWER_COST, plantType);
    }

    @Override
    public Sunflower createDoubleSunflower(final Point2D pos, final Plants plantType) {
        return new SunflowerImpl(pos, new TimerImpl(STANDARD_SUNPRODUCING_TIME / 2), STANDARD_SUNFLOWER_HEALTH, FAST_SUNFLOWER_COST, plantType);
    }
    
}
