package flowerforce.view.game;

import flowerforce.view.entities.CardView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;

import java.net.URL;
import java.util.*;

public class ShopSceneController implements Initializable {

    @FXML
    private ImageView imgBuy;
    @FXML
    private ImageView imgItemToSell;
    @FXML
    private Label lblCoins;
    @FXML
    private Label lblCostItem;

    private static final Effect BLACK_WHITE_EFFECT = new ColorAdjust(0,-1,0,0);
    private final FlowerForceApplication application;
    private final List<Pair<ImageView,CardView>> imagePlants = new LinkedList<>();
    private final Map<CardView,Boolean> purchasablePlants = new HashMap<>();
    private int index = 0;
    /**
     * Creates a new controller for a How To Play scene.
     * @param application the application to communicate scene changes
     */
    public ShopSceneController(final FlowerForceApplication application) {
        this.application = application;
    }

    @FXML
    void buyItem(MouseEvent event) {
        var bought = this.application.getController().buyPlant(imagePlants.get(index).getValue());
        purchasablePlants.put(
                imagePlants.get(index).getValue(),
                !bought
        );
        lblCoins.setText(this.application.getController().getPlayerCoins() + "$");
        changePlant();

    }

    @FXML
    void mainMenu(MouseEvent event) {
        this.application.menu();
    }

    @FXML
    void nextItem(MouseEvent event) {
        index++;
        if(index == purchasablePlants.size()) {
            index = 0;
        }
        this.changePlant();
    }

    @FXML
    void prevItem(MouseEvent event) {
        if(index == 0) {
            index = purchasablePlants.size();
        }
        index--;
        this.changePlant();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblCoins.setText(this.application.getController().getPlayerCoins() + "$");
        var tmpPurchasablePlants = this.application.getController().getPurchasablePlants();
        tmpPurchasablePlants.keySet().forEach(p ->
            {
                imagePlants.add(new Pair<>(new ImageView(p.getMenuImage()), p));
                purchasablePlants.put(p, tmpPurchasablePlants.get(p));
            }
        );
        this.changePlant();
    }

    private void changePlant() {
        this.imgItemToSell.setImage(imagePlants.get(index).getKey().getImage());
        this.lblCostItem.setText("" + imagePlants.get(index).getValue().getCost());
        if (purchasablePlants.get(imagePlants.get(index).getValue())) {
            this.imgItemToSell.setEffect(null);
            this.imgBuy.setDisable(false);
        } else {
            this.imgItemToSell.setEffect(BLACK_WHITE_EFFECT);
            this.imgBuy.setDisable(true);
        }
    }
}
