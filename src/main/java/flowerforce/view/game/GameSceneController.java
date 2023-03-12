package flowerforce.view.game;

import java.awt.Dimension;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import flowerforce.view.entities.EntityView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public final class GameSceneController implements Initializable, GameEngine {

    @FXML private AnchorPane gamePane;

    @FXML private ImageView imgBackground;

    @FXML private GridPane griglia;

    @FXML private Button btnSunflower;

    @FXML private Button btnPeashooter;

    @FXML private Label lblSunCounter;

    @FXML private Canvas cnvYard;

    @FXML private ImageView imageMenu;

    @FXML private ImageView imageResult;

    private final FlowerForceApplication application;
    private final Dimension size;
    private final Set<EntityView> entities = new HashSet<>();

    public GameSceneController(final FlowerForceApplication application, final Dimension size) {
        this.application = application;
        this.size = size;
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
        this.application.getController().placePlant(getRow(event.getY()), getColumn(event.getX()));
        //lblSunCounter.setText(Integer.toString(controller.getSunCounter()));
    }

    @FXML
    void selectMenu( final MouseEvent event) {
        imageResult.setVisible(false);
        imageMenu.setVisible(false);
        imageResult.setDisable(true);
        System.out.println("prova");
        application.menu();
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
        lblSunCounter.setText(Integer.toString(this.application.getController().getSunCounter()));
    }

    private void setWindowSize() {
        imgBackground.setFitHeight(size.getHeight());
        imgBackground.setFitWidth(size.getWidth());
        gamePane.setPrefWidth(size.getWidth());
        gamePane.setPrefHeight(size.getHeight());
        //TODO: find the correct ratio between canvas and gamePane size
        cnvYard.setWidth(size.getWidth()*0.8);
        cnvYard.setHeight(size.getHeight()*0.8);
    }    

    @Override
    public void addEntity(EntityView entity) {
        entities.add(entity);
    }

    @Override
    public void removeEntity(EntityView entity) {
        entities.remove(entity);
    }

    @Override
    public void clearEntities() {
        entities.clear();
    }

    @Override
    public void render() {
        this.clearCanvas();
        entities.forEach(e -> this.draw(e.getImage(), e.getPlacingPosition()));
    }

    private void clearCanvas() {
        GraphicsContext gc = this.cnvYard.getGraphicsContext2D();
        gc.clearRect(0, 0, size.getWidth(), size.getHeight());
    }

    private void draw(final Image image, final Point2D pos) {
        GraphicsContext gc = this.cnvYard.getGraphicsContext2D();
        //TODO: must resize correctly the image, depending on screen size
        gc.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), pos.getX(), pos.getY(), image.getWidth(), image.getHeight());
    }

    @Override
    public Dimension getFieldSize() {
        return new Dimension((int) this.cnvYard.getWidth(), (int) this.cnvYard.getHeight());
    }

    @Override
    public void over( final boolean isWon) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'over'");
        imageResult.setVisible(true);
        imageMenu.setVisible(true);
        imageMenu.setDisable(false);
        if ( isWon) {
            imageResult.setImage(new Image("..\\images\\LevelWin.png"));
        }
        else {
            imageResult.setImage(new Image("..\\images\\ZombiesAteYourBrains.png"));
        }
    }
}
