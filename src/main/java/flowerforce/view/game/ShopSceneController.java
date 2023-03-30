package flowerforce.view.game;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ShopSceneController implements Initializable {

    @FXML
    private ImageView imgItemToSell;
    @FXML
    private Label lblCoins;
    @FXML
    private Label lblCostItem;

    private final FlowerForceApplication application;

    /**
     * Creates a new controller for a How To Play scene.
     * @param application the application to communicate scene changes
     */
    public ShopSceneController(final FlowerForceApplication application) {
        this.application = application;
    }

    @FXML
    void buyItem(MouseEvent event) {
        System.out.println("buy");
    }

    @FXML
    void mainMenu(MouseEvent event) {
        this.application.menu();
    }

    @FXML
    void nextItem(MouseEvent event) {
        System.out.println("next");
    }

    @FXML
    void prevItem(MouseEvent event) {
        System.out.println("prev");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblCoins.setText(this.application.getController().getPlayerCoins() + "$");
    }
}
