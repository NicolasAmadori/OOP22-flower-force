package flowerforce.view.game;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ShopSceneController {

    @FXML
    private ImageView imgItemToSell;

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
}
