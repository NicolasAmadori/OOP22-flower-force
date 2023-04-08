package flowerforce.controller.utilities;

import flowerforce.common.ResourceFinder;
import flowerforce.model.entities.PlantInfo;
import flowerforce.view.entities.CardView;
import flowerforce.view.entities.CardViewImpl;

/**
 * Utility class to generate Cardviews.
 */
public final class CardGenerator {
    private static final String CARD_SUFFIX = "_card";

    private static final String IMAGES_EXTENSION = ".png";

    private CardGenerator() {

    }

    /**
     * Generate the card view from the info of the plant.
     * @param plantInfo the information of the plant (name and cost)
     * @return The cardView instance
     */
    public static CardView getCardView(final PlantInfo plantInfo) {
        final String completeImagePath = ResourceFinder.getPlantImagePath(
                plantInfo.getName().concat(CARD_SUFFIX).concat(IMAGES_EXTENSION)).toExternalForm();
        return new CardViewImpl(plantInfo.getCost(), completeImagePath);
    }
}
