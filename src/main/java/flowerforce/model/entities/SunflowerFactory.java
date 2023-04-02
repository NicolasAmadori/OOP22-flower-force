package flowerforce.model.entities;

import javafx.geometry.Point2D;

public interface SunflowerFactory {
    
    Sunflower createCommonSunflower(Point2D pos);

    Sunflower createDoubleSunflower(Point2D pos);
}
