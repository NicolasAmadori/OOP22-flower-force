package flowerforce.view.game;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import flowerforce.controller.GameController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.canvas.Canvas;

/**
 * This is an implementation of {@link GameController}.
 */
public final class GameSceneController implements GameController, Initializable {

    @FXML
    private AnchorPane gamePane;

    @FXML
    private ImageView imgBackground;

    @FXML
    private GridPane griglia;

    @FXML
    private Button btnSunflower;

    @FXML
    private Button btnPeashooter;

    @FXML
    private Label lblSunCounter;

    @FXML
    private Canvas cnvYard;

    private FlowerForceApplication application;

    public GameSceneController(FlowerForceApplication application) {
        this.application = application;
    }

    @FXML
    void selectPeashooter(final ActionEvent event) {
        System.out.println("Peashooter selected"); //TODO: remove
    }

    @FXML
    void selectSunflower(final ActionEvent event) {
        System.out.println("Sunflower selected"); //TODO: remove
    }

    @FXML
    void canvasClicked(final MouseEvent event) {
        System.out.println(getRow(event.getY()) + " " + getColumn(event.getX()));
    }

    private int getRow(final double y) {
        return getGridIndex(y, cnvYard.getHeight(), 5); //TODO: remove magic number
    }

    private int getColumn(final double x) {
        return getGridIndex(x, cnvYard.getWidth(), 9); //TODO: remove magic number
    }

    private int getGridIndex(final double val, final double totalLength, final int nSlices) {
        final double span = totalLength / nSlices;
        for (int r = nSlices - 1; r >= 0; r--) {
            if (val >= r * span) {
                return r;
            }
        }
        return 0;
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        setWindowSize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPlayerCoins() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPlayerCoins'");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Integer, Boolean> getLevelIds() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLevelIds'");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startLevelGame(final int levelId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'startLevelGame'");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startInfiniteGame() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'startInfiniteGame'");
    }

    private void setWindowSize() {
        //final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //imgBackground.setFitHeight(screenSize.getHeight());
        //imgBackground.setFitWidth(screenSize.getWidth());
        //gamePane.setPrefWidth(screenSize.getWidth());
        //gamePane.setPrefHeight(screenSize.getHeight());
    }
}
