package flowerforce.view.game;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import flowerforce.view.entities.CardView;
import flowerforce.view.utilities.SoundManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;

import java.util.Map;
import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;

/**
 * Manages JavaFX Shop scene.
 */
public final class ShopSceneController {
    @FXML
    private ImageView imgBuy;
    @FXML
    private ImageView imgItemToSell;
    @FXML
    private Label lblCoins;
    @FXML
    private Label lblCostItem;

    private static final Effect BLACK_WHITE_EFFECT = new ColorAdjust(0, -1, 0, 0);
    private final FlowerForceApplication application;
    private final List<Pair<ImageView, CardView>> imagePlants = new LinkedList<>();
    private final Map<CardView, Boolean> purchasablePlants = new HashMap<>();
    private int index;

    /**
     * Creates a new controller for a Shop scene.
     * @param application the application to communicate scene changes
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Cannot copy application because the instance must remain the same for future usages"
    )
    public ShopSceneController(final FlowerForceApplication application) {
        this.application = application;
    }

    @FXML
    void buyItem(final MouseEvent event) {
        final var bought = this.application.getController().buyPlant(imagePlants.get(index).getValue());
        purchasablePlants.put(
                imagePlants.get(index).getValue(),
                !bought
        );
        if (bought) {
            SoundManager.openShop();
        }
        lblCoins.setText(this.application.getController().getPlayerCoins() + "$");
        changePlant();

    }

    @FXML
    void mainMenu(final MouseEvent event) {
        this.application.menu();
    }

    @FXML
    void nextItem(final MouseEvent event) {
        index++;
        if (index == purchasablePlants.size()) {
            index = 0;
        }
        this.changePlant();
    }

    @FXML
    void prevItem(final MouseEvent event) {
        if (index == 0) {
            index = purchasablePlants.size();
        }
        index--;
        this.changePlant();
    }

    /**
     * Sets Shop dates.
     */
    public void initialize() {
        lblCoins.setText(this.application.getController().getPlayerCoins() + "$");
        final var tmpPurchasablePlants = this.application.getController().getPurchasablePlants();
        tmpPurchasablePlants.keySet().forEach(p -> {
                imagePlants.add(new Pair<>(new ImageView(p.getMenuImage()), p));
                purchasablePlants.put(p, tmpPurchasablePlants.get(p));
            }
        );
        this.changePlant();
    }

    /*
    Used to update the shop view
     */
    private void changePlant() {
        this.imgItemToSell.setImage(imagePlants.get(index).getKey().getImage());
        this.lblCostItem.setText(Integer.toString(imagePlants.get(index).getValue().getCost()));
        if (purchasablePlants.get(imagePlants.get(index).getValue())) {
            this.imgItemToSell.setEffect(null);
            this.imgBuy.setDisable(false);
        } else {
            this.imgItemToSell.setEffect(BLACK_WHITE_EFFECT);
            this.imgBuy.setDisable(true);
        }
    }
}
