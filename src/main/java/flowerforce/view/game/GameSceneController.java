package flowerforce.view.game;

import java.awt.*;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.List;
import java.util.Map;

import flowerforce.view.entities.EntityTypeView;
import flowerforce.view.entities.EntityView;
import flowerforce.view.entities.EntityViewId;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public final class GameSceneController implements Initializable, GameEngine {

    @FXML private AnchorPane gamePane;

    @FXML private ImageView imgBackground;

    @FXML private GridPane griglia;

    @FXML private Label lblSunCounter;

    @FXML private ImageView imgSunflower;

    @FXML private ImageView imgPeashooter;

    @FXML private ImageView imgSnowshooter;

    @FXML private ImageView imgFastshooter;

    @FXML private ImageView imgFireshooter;

    @FXML private Label lblSunflower;

    @FXML private Label lblPeashooter;

    @FXML private Label lblSnowshooter;

    @FXML private Label lblFastshooter;

    @FXML private Label lblFireshooter;

    @FXML private ImageView imageMenu;

    @FXML private ImageView imageResult;

    //Garden size: 1920x1080, yard size: 1320x880. Down-shift: 150px, right-shift: 600px.
    private static final int COLS = 9;
    private static final int ROWS = 5;
    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;
    private static final double RIGHTSHIFT_RATIO = 600.0 / 1920.0;
    private static final double DOWNSHIFT_RATIO = 150.0 / 1080.0;
    private static final double YARDWIDTH_RATIO = 1320.0 / 1920.0;
    private static final double YARDHEIGHT_RATIO = 880.0 / 1080.0;
    private static final double IMAGEWIDTH_RATIO = 148.0 / 1920.0;
    private static final double IMAGEHEIGHT_RATIO = 146.0 / 1080.0;
    private static final double BLOOM_EFFECT_VALUE = 0.65;
    private final Dimension2D stdSize = new Dimension2D(WIDTH, HEIGHT); //TODO: remove magic numbers
    private final FlowerForceApplication application;
    private final Set<EntityView> entities = new HashSet<>();
    private final Set<ImageView> entityImages = new HashSet<>();
    private final Map<EntityViewId, ImageView> cards = new HashMap<>();
    private final Map<EntityViewId, Label> cardLabels = new HashMap<>();
    private final Point2D firstYardPoint;
    private final Dimension2D yardDimension;
    private final Dimension2D imageDimension;
    private final Effect bloomEffect = new Bloom(BLOOM_EFFECT_VALUE);
    private EntityViewId cardSelected = null;

    public GameSceneController(final FlowerForceApplication application) {
        this.application = application;
        this.firstYardPoint = new Point2D((int) (stdSize.getWidth() * RIGHTSHIFT_RATIO), (int) (stdSize.getHeight() * DOWNSHIFT_RATIO));
        this.yardDimension = new Dimension2D((int) (stdSize.getWidth() * YARDWIDTH_RATIO), (int) (stdSize.getHeight() * YARDHEIGHT_RATIO));
        this.imageDimension = new Dimension2D((int) (stdSize.getWidth() * IMAGEWIDTH_RATIO), (int) (stdSize.getHeight() * IMAGEHEIGHT_RATIO));
        this.application.getController().setGameEngine(this);
    }

    private void loadEntityCards() {
        this.cardLabels.putAll(Map.of(EntityViewId.SUNFLOWER, lblSunflower, EntityViewId.PEASHOOTER, lblPeashooter, 
            EntityViewId.SNOWSHOOTER, lblSnowshooter, EntityViewId.FASTSHOOTER, lblFastshooter, EntityViewId.FIRESHOOTER, lblFireshooter));
        this.cards.putAll(Map.of(EntityViewId.SUNFLOWER, imgSunflower, EntityViewId.PEASHOOTER, imgPeashooter, EntityViewId.SNOWSHOOTER, imgSnowshooter,
            EntityViewId.FASTSHOOTER, imgFastshooter, EntityViewId.FIRESHOOTER, imgFireshooter));
        this.cardLabels.values().forEach(l -> l.setText(Integer.toString(100)));
    }

    private void addBloomEffect() {
        if (this.cardSelected != null) {
            this.cards.get(this.cardSelected).setEffect(bloomEffect);
        }
    }

    private void removeBloomEffect() {
        if (this.cardSelected != null) {
            this.cards.get(this.cardSelected).setEffect(null);
        }
    }

    @FXML
    void selectSunflower(final MouseEvent event) {
        this.removeBloomEffect();
        this.cardSelected = EntityViewId.SUNFLOWER;
        this.addBloomEffect();
    }

    @FXML
    void selectPeashooter(final MouseEvent event) {
        this.removeBloomEffect();
        this.cardSelected = EntityViewId.PEASHOOTER;
        this.addBloomEffect();
    }

    @FXML
    void selectSnowshooter(final MouseEvent event) {
        this.removeBloomEffect();
        this.cardSelected = EntityViewId.SNOWSHOOTER;
        this.addBloomEffect();
    }

    @FXML
    void selectFastshooter(final MouseEvent event) {
        this.removeBloomEffect();
        this.cardSelected = EntityViewId.FASTSHOOTER;
        this.addBloomEffect();
    }

    @FXML
    void selectFireshooter(final MouseEvent event) {
        this.removeBloomEffect();
        this.cardSelected = EntityViewId.FIRESHOOTER;
        this.addBloomEffect();
    }

    @FXML
    void yardClicked(final MouseEvent event) {
        if (event.getX() > this.firstYardPoint.getX() && event.getY() > this.firstYardPoint.getY()) {
            System.out.println(getRow(event.getY() - this.firstYardPoint.getY()) + " " + getColumn(event.getX() - this.firstYardPoint.getX()));
            //this.application.getController().placePlant(getRow(event.getY()), getColumn(event.getX()));
        } else {
            this.removeBloomEffect();
            this.cardSelected = null;
        }
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
        return getGridIndex(y, this.yardDimension.getHeight(), ROWS); //TODO: remove magic number
    }

    private int getColumn(final double x) {
        return getGridIndex(x, this.yardDimension.getWidth(), COLS); //TODO: remove magic number
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
        this.updateSunCounter();
        this.loadEntityCards();
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
        this.gamePane.getChildren().stream().filter(n -> this.entityImages.contains(n)).forEach(n -> this.gamePane.getChildren().remove(n));
        this.entityImages.clear();
        entities.forEach(e -> this.drawEntity(e.getPlaceableImage(), e.getPlacingPosition()));
        this.updateSunCounter();
    }

    private void updateSunCounter() {
        //this.lblSunCounter.setText(Integer.toString(this.application.getController().getSunCounter()));
    }

    private void drawEntity(final Image image, final Point2D pos) {
        ImageView iv = new ImageView(image);
        iv.relocate(this.firstYardPoint.getX() + pos.getX(), this.firstYardPoint.getY() + pos.getY());
        iv.setPreserveRatio(true);
        iv.setFitWidth(this.imageDimension.getWidth());
        iv.setFitHeight(this.imageDimension.getHeight());
        this.entityImages.add(iv);
        this.gamePane.getChildren().add(iv);
    }

    @Override
    public Dimension2D getYardSize() {
        return this.yardDimension;
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
