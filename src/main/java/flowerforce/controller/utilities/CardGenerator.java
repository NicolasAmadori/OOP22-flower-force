package flowerforce.controller.utilities;

import flowerforce.common.ResourceFinder;
import flowerforce.model.entities.PlantInfo;
import flowerforce.view.entities.CardView;
import flowerforce.view.entities.CardViewImpl;

public final class CardGenerator {
    private static final String CARD_SUFFIX = "_card";
    private static final String IMAGES_EXTENSION = ".png";
    public static CardView getCardView(final PlantInfo plantInfo) {
        final String completeImagePath = ResourceFinder.getPlantImagePath(
                plantInfo.getName().concat(CARD_SUFFIX).concat(IMAGES_EXTENSION));
        return new CardViewImpl(plantInfo.getCost(), completeImagePath);
    }
}
