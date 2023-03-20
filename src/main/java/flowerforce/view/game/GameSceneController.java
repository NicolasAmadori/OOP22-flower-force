package flowerforce.view.game;

import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import flowerforce.view.entities.CardView;
import flowerforce.view.entities.CardViewImpl;
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
import javafx.scene.shape.Rectangle;

public final class GameSceneController implements Initializable, GameEngine {

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

    //Garden size: 1920x1080, yard size: 1320x880. Down-shift: 150px, right-shift: 600px.
    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;
    private static final double RIGHTSHIFT_RATIO = 600.0 / 1920.0;
    private static final double DOWNSHIFT_RATIO = 150.0 / 1080.0;
    private static final double YARDWIDTH_RATIO = 1320.0 / 1920.0;
    private static final double YARDHEIGHT_RATIO = 880.0 / 1080.0;
    private static final double IMG_RESIZE_FACTOR = 2.0;
    private static final double BLOOM_EFFECT_VALUE = 0.65;
    private final int rows;
    private final int cols;
    private final Dimension2D stdSize = new Dimension2D(WIDTH, HEIGHT);
    private final FlowerForceApplication application;
    private final Set<ImageView> entityImages = new HashSet<>();
    private final List<ImageView> cards = new LinkedList<>();
    private final List<Label> cardLabels = new LinkedList<>();
    private final Point2D firstYardPoint;
    private final Dimension2D yardDimension;
    private final Dimension2D cellDimension;
    private final Effect bloomEffect = new Bloom(BLOOM_EFFECT_VALUE);
    private Optional<Integer> cardSelected = Optional.empty();

    public GameSceneController(final FlowerForceApplication application) {
        this.application = application;
        this.firstYardPoint = new Point2D((int) (this.stdSize.getWidth() * RIGHTSHIFT_RATIO), (int) (this.stdSize.getHeight() * DOWNSHIFT_RATIO));
        this.yardDimension = new Dimension2D((int) (this.stdSize.getWidth() * YARDWIDTH_RATIO), (int) (this.stdSize.getHeight() * YARDHEIGHT_RATIO));
        this.application.getController().setGameEngine(this);
        this.rows = this.application.getController().getTotalRows();
        this.cols = this.application.getController().getTotalColumns();
        this.cellDimension = new Dimension2D(this.yardDimension.getWidth() / this.cols, this.yardDimension.getHeight() / this.rows);
    }

    private void loadEntityCards() {
        //List<CardView> cardViews = this.application.getController().getCards();
        List<CardView> cardViews = List.of(new CardViewImpl(50, "flowerforce/game/images/sunflower.png")); //TODO:remove
        
        this.cardLabels.addAll(List.of(lbl0, lbl1, lbl2, lbl3, lbl4));
        this.cards.addAll(List.of(card0, card1, card2, card3, card4));
        for (int i = 0; i < cardLabels.size() && i < cards.size(); i++) {
            if (i < cardViews.size()) {
                this.cards.get(i).setImage(cardViews.get(i).getMenuImage());
                this.cardLabels.get(i).setText(String.valueOf(cardViews.get(i).getCost()));
            } else {
                this.cards.get(i).setVisible(false);
                this.cardLabels.get(i).setVisible(false);
            }
        }
    }

    private void addBloomEffect() {
        this.cardSelected.ifPresent(i -> this.cards.get(i).setEffect(this.bloomEffect));
    }

    private void removeBloomEffect() {
        this.cardSelected.ifPresent(i -> this.cards.get(i).setEffect(null));
    }

    @FXML
    void selectCard(final MouseEvent event) {
        this.removeBloomEffect();
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
        if (this.cardSelected.isPresent() && isInsideYard(event.getX(), event.getY())) {
            System.out.println(getRow(event.getY() - this.firstYardPoint.getY()) + " " + getColumn(event.getX() - this.firstYardPoint.getX())); //TODO: remove
            this.application.getController().placePlant(this.cardSelected.get(), this.getRow(event.getY()), getColumn(event.getX()));
        }
        this.removeBloomEffect();
        this.cardSelected = Optional.empty();
    }

    @FXML
    void mouseMoved(final MouseEvent event) {
        if (isInsideYard(event.getX(), event.getY())) {
            this.coloredCell.relocate(this.firstYardPoint.getX() + (getColumn(event.getX() - this.firstYardPoint.getX()) * this.cellDimension.getWidth()),
                    this.firstYardPoint.getY() + getRow(event.getY() - this.firstYardPoint.getY()) * this.cellDimension.getHeight());
            this.coloredCell.setVisible(true);
        } else {
            this.coloredCell.setVisible(false);
        }
    }

    @FXML
    void selectMenu(final MouseEvent event) {
        imageResult.setVisible(false);
        imageMenu.setVisible(false);
        imageResult.setDisable(true);
        System.out.println("prova");
        application.menu();
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
    public void render() {
        //TODO: decomment
        //this.enableCards();
        this.clearDrawnEntities();
        this.application.getController().getPlacedEntities().forEach(e -> this.drawEntity(e.getPlaceableImage(), e.getPlacingPosition()));
        this.updateSunCounter();
    }

    private void enableCards() {
        this.cards.forEach(c -> c.setDisable(true));
        this.application.getController().getEnabledCards().forEach(i -> {
            if (i < this.cards.size()) {
                this.cards.get(i).setDisable(false);
            }
        });
    }

    private void clearDrawnEntities() {
        this.gamePane.getChildren().stream().filter(n -> this.entityImages.contains(n)).forEach(n -> this.gamePane.getChildren().remove(n));
        this.entityImages.clear();
    }

    private void updateSunCounter() {
        //this.lblSunCounter.setText(Integer.toString(this.application.getController().getSunCounter()));
    }

    private void drawEntity(final Image image, final Point2D pos) {
        ImageView iv = new ImageView(image);
        iv.relocate(this.firstYardPoint.getX() + pos.getX(), this.firstYardPoint.getY() + pos.getY());
        iv.setPreserveRatio(true);
        iv.setFitWidth(image.getWidth() * IMG_RESIZE_FACTOR);
        iv.setFitHeight(image.getHeight() * IMG_RESIZE_FACTOR);
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

    @Override
    public double getImageResizeFactor() {
        return IMG_RESIZE_FACTOR;
    }
}
