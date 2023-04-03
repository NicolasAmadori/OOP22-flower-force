package flowerforce.controller.utilities;

import flowerforce.common.ResourceFinder;
import flowerforce.view.entities.CardView;
import flowerforce.view.entities.CardViewImpl;
import javafx.util.Pair;

public final class CardGenerator {
    private static final String CARD_SUFFIX = "_card";
    private static final String IMAGES_EXTENSION = ".png";
    public static CardView getCardView(final Pair<String, Integer> plantInfo) {
        final String completeImagePath = ResourceFinder.getPlantImagePath(
                plantInfo.getKey().concat(CARD_SUFFIX).concat(IMAGES_EXTENSION));
        return new CardViewImpl(plantInfo.getValue(), completeImagePath);
    }
}
