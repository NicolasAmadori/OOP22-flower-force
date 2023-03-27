package flowerforce.view.game;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.net.URL;
import java.util.*;

import flowerforce.common.ResourceFinder;
import flowerforce.view.entities.CardView;
import flowerforce.view.entities.EntityView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

public final class GameSceneController implements GameEngine, Initializable {

    @FXML private AnchorPane gamePane;

    @FXML private ImageView imgBackground;

    @FXML private Label lblSunCounter;

    @FXML private ImageView card0;

    @FXML private ImageView card1;

    @FXML private ImageView card2;

    @FXML private ImageView card3;

    @FXML private ImageView card4;

    @FXML private Label lbl0;

    @FXML private Label lbl1;

    @FXML private Label lbl2;

    @FXML private Label lbl3;

    @FXML private Label lbl4;

    @FXML private ImageView imageMenu;

    @FXML private ImageView imageResult;

    @FXML private Rectangle coloredCell;

    @FXML private ImageView imageShovel;

    @FXML private ImageView transparentShovel;

    //Garden size: 1920x1080, yard size: 1320x880. Down-shift: 150px, right-shift: 600px.
    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;
    private static final double RIGHTSHIFT_RATIO = 600.0 / 1920.0;
    private static final double DOWNSHIFT_RATIO = 150.0 / 1080.0;
    private static final double YARDWIDTH_RATIO = 1314.0 / 1920.0;
    private static final double YARDHEIGHT_RATIO = 880.0 / 1080.0;
    private static final double IMG_RESIZE_FACTOR = 2.0;
    private static final Effect BLOOM_EFFECT = new Bloom(0.65);
    private static final Effect RESET_BLOOM = new Bloom(1);
    private static final Effect BLACK_WHITE = new ColorAdjust(0,-1,0,0);
    private static final Effect RESET_COLORS = new ColorAdjust(0,0,0,0);
    private final int rows;
    private final int cols;
    private final FlowerForceApplication application;
    private final Map<EntityView, ImageView> drawnEntities = new HashMap<>();
    private final List<ImageView> cards = new LinkedList<>();
    private final List<Label> cardLabels = new LinkedList<>();
    private final Point2D firstYardPoint;
    private final Dimension2D yardDimension;
    private final Dimension2D cellDimension;
    private Optional<Integer> cardSelected = Optional.empty();
    private boolean isShovelSelected = false;

    public GameSceneController(final FlowerForceApplication application) {
        this.application = application;
        this.firstYardPoint = new Point2D((int) (WIDTH * RIGHTSHIFT_RATIO), (int) (HEIGHT * DOWNSHIFT_RATIO));
        this.yardDimension = new Dimension2D((int) (WIDTH * YARDWIDTH_RATIO), (int) (HEIGHT * YARDHEIGHT_RATIO));
        this.application.getController().setGameEngine(this);
        this.rows = this.application.getController().getTotalRows();
        this.cols = this.application.getController().getTotalColumns();
        this.cellDimension = new Dimension2D(this.yardDimension.getWidth() / this.cols, this.yardDimension.getHeight() / this.rows);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadCards(final List<CardView> cardViews) {
        this.cardLabels.addAll(List.of(lbl0, lbl1, lbl2, lbl3, lbl4));
        this.cards.addAll(List.of(card0, card1, card2, card3, card4));
        for (int i = 0; i < cardLabels.size() && i < cards.size(); i++) {
            if (i < cardViews.size()) {
                this.cards.get(i).setImage(cardViews.get(i).getMenuImage());
                this.cardLabels.get(i).setText(String.valueOf(cardViews.get(i).getCost()));
                this.cards.get(i).setVisible(true);
                this.cardLabels.get(i).setVisible(true);
            } else {
                this.cards.get(i).setVisible(false);
                this.cardLabels.get(i).setVisible(false);
                this.cards.remove(i);
                this.cardLabels.remove(i);
            }
        }
    }

    private void addBloomEffect() {
        if (this.isShovelSelected) {
            this.imageShovel.setEffect(BLOOM_EFFECT);
        } else {
            this.cardSelected.ifPresent(i -> this.cards.get(i).setEffect(BLOOM_EFFECT));
        }
    }

    private void removeBloomEffect() {
        if (this.isShovelSelected) {
            this.imageShovel.setEffect(RESET_BLOOM);
        } else {
            this.cardSelected.ifPresent(i -> this.cards.get(i).setEffect(RESET_BLOOM));
        }
    }

    @FXML
    void shovelSelected(final MouseEvent event) {
        this.removeBloomEffect();
        this.cardSelected = Optional.empty();
        this.isShovelSelected = true;
        this.addBloomEffect();        
    }

    @FXML
    void selectCard(final MouseEvent event) {
        this.removeBloomEffect();
        this.isShovelSelected = false;
        this.cardSelected = Optional.of(cards.indexOf((ImageView) (event.getSource())));
        this.addBloomEffect();
    }

    private boolean isInsideYard(double x, double y) {
        return x >= this.firstYardPoint.getX() && y >= this.firstYardPoint.getY()
                && x < this.firstYardPoint.getX() + this.yardDimension.getWidth() 
                && y < this.firstYardPoint.getY() + this.yardDimension.getHeight();
    }

    @FXML
    void yardClicked(final MouseEvent event) {
        if (isInsideYard(event.getX(), event.getY())) {
            if (this.cardSelected.isPresent()) {
                if (this.application.getController().placePlant(this.cardSelected.get(), this.getRow(event.getY() - this.firstYardPoint.getY()),
                        this.getColumn(event.getX() - this.firstYardPoint.getX()))) {
                    this.removeBloomEffect();
                    this.cardSelected = Optional.empty();
                }
            } else if (this.isShovelSelected) {
                if (this.application.getController().removePlant(this.getRow(event.getY() - this.firstYardPoint.getY()), this.getColumn(event.getX() - this.firstYardPoint.getX()))) {
                    this.removeBloomEffect();
                    this.isShovelSelected = false;
                }
            }            
        } else {
            this.removeBloomEffect();
            this.cardSelected = Optional.empty();
            this.isShovelSelected = false;
        }
        
    }

    @FXML
    void mouseMoved(final MouseEvent event) {
        if ((this.isShovelSelected || this.cardSelected.isPresent()) && isInsideYard(event.getX(), event.getY())) {
            this.coloredCell.relocate(this.firstYardPoint.getX() + getColumn(event.getX() - this.firstYardPoint.getX()) * this.cellDimension.getWidth(),
                    this.firstYardPoint.getY() + getRow(event.getY() - this.firstYardPoint.getY()) * this.cellDimension.getHeight());
            this.coloredCell.setVisible(true);
        } else {
            this.coloredCell.setVisible(false);
        }
    }

    @FXML
    void selectMenu(final MouseEvent event) {
        this.application.menu();
    }

    private int getRow(final double y) {
        return getGridIndex(y, this.yardDimension.getHeight(), this.rows);
    }

    private int getColumn(final double x) {
        return getGridIndex(x, this.yardDimension.getWidth(), this.cols);
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
     * {@inheritDoc}
     */
    @Override
    public void render() {
        this.enableCards();
        this.updateEntities(this.application.getController().getPlacedEntities());        
        this.updateSunCounter();
    }

    private void enableCards() {
        final Set<Integer> enabledCards = this.application.getController().getEnabledCards();
        this.cards.forEach(c -> {
            if (enabledCards.contains(cards.indexOf(c))) {
                if (c.isDisable()) {
                    c.setEffect(RESET_COLORS);
                    c.setDisable(false);                  
                }
            } else {
                c.setEffect(BLACK_WHITE);
                c.setDisable(true);
            }
        });
    }

    private void updateEntities(final Set<EntityView> newEntities) {
        final Set<EntityView> toRemove =  this.drawnEntities.keySet().stream()
                .filter(e -> !newEntities.contains(e))
                .collect(Collectors.toSet());
        toRemove.forEach(e -> {
            this.gamePane.getChildren().remove(this.drawnEntities.get(e));
            this.drawnEntities.remove(e);
        });
        newEntities.stream()
                .filter(e -> this.drawnEntities.containsKey(e))
                .forEach(e -> {
                    this.drawnEntities.get(e).relocate(e.getPlacingPosition().getX() + this.firstYardPoint.getX(), e.getPlacingPosition().getY() + this.firstYardPoint.getY());
                    this.drawnEntities.get(e).setImage(e.getPlaceableImage());
                });
        newEntities.stream()
                .filter(e -> !this.drawnEntities.containsKey(e))
                .forEach(e -> {
                    ImageView iv = toImageView(e.getPlaceableImage(), e.getPlacingPosition());
                    this.drawnEntities.put(e, iv);
                    this.gamePane.getChildren().add(iv);
                });
    }

    private void updateSunCounter() {
        final int newSunCounter = this.application.getController().getSunCounter();
        if (newSunCounter != Integer.parseInt(this.lblSunCounter.getText())) {
            this.lblSunCounter.setText(Integer.toString(newSunCounter));
        }
    }

    private ImageView toImageView(final Image image, final Point2D pos) {
        ImageView iv = new ImageView(image);
        iv.relocate(this.firstYardPoint.getX() + pos.getX(), this.firstYardPoint.getY() + pos.getY());
        iv.setPreserveRatio(true);
        iv.setFitWidth(image.getWidth() * IMG_RESIZE_FACTOR);
        iv.setFitHeight(image.getHeight() * IMG_RESIZE_FACTOR);
        iv.setMouseTransparent(true);
        return iv;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dimension2D getYardDimension() {
        return this.yardDimension;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void over(final boolean isWon) {
        this.imageResult.setVisible(true);
        this.imageMenu.setVisible(true);
        this.imageMenu.setDisable(false);
        this.imageMenu.toFront();
        this.imageResult.toFront();
        this.cards.forEach(card -> card.setDisable(true));
        if (isWon) {
            imageResult.setImage(new Image(ResourceFinder.getImagePath("victory.png")));
        } else {
            imageResult.setImage(new Image(ResourceFinder.getImagePath("loss.png")));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getImageResizeFactor() {
        return IMG_RESIZE_FACTOR;
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
    public void initialize(URL location, ResourceBundle resources) {
        this.card0.setDisable(false);
        this.card1.setDisable(false);
    }
}
