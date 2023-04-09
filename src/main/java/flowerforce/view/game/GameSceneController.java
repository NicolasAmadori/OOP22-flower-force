package flowerforce.view.game;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import flowerforce.common.ResourceFinder;
import flowerforce.view.entities.CardView;
import flowerforce.view.entities.EntityView;
import flowerforce.view.utilities.SoundManager;
import javafx.fxml.FXML;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

/**
 * This class is the JavaFx controller of the {@link GameScene}.
 * It also implements {@link GameEngine} interface methods, needed by the main controller.
 */
public final class GameSceneController implements GameEngine {

    @FXML private AnchorPane gamePane;
    @FXML private Label lblSunCounter;
    @FXML private ImageView card0;
    @FXML private ImageView card1;
    @FXML private ImageView card2;
    @FXML private ImageView card3;
    @FXML private ImageView card4;
    @FXML private ImageView card5;
    @FXML private ImageView card6;
    @FXML private ImageView card7;
    @FXML private ImageView card8;
    @FXML private Label lbl0;
    @FXML private Label lbl1;
    @FXML private Label lbl2;
    @FXML private Label lbl3;
    @FXML private Label lbl4;
    @FXML private Label lbl5;
    @FXML private Label lbl6;
    @FXML private Label lbl7;
    @FXML private Label lbl8;
    @FXML private ImageView imageResult;
    @FXML private StackPane resultPane;
    @FXML private Rectangle coloredCell;
    @FXML private ImageView imageShovel;
    @FXML private ProgressBar progressBar;
    @FXML private Label lblScore;

    //Garden size: 1920x1080, yard size: 1320x880. Down-shift: 150px, right-shift: 600px.
    private static final int YARD_FIRST_X = 600;
    private static final int YARD_FIRST_Y = 150;
    private static final double YARD_WIDTH = 1314.0;
    private static final double YARD_HEIGHT = 880.0;
    private static final Effect BLOOM_EFFECT = new Bloom(0.65);
    private static final Effect BLACK_WHITE_EFFECT = new ColorAdjust(0, -1, 0, 0);
    private static final Effect DAMAGE_EFFECT = new ColorAdjust(0, 0,  0.5, 0);
    private final int rows;
    private final int cols;
    private final FlowerForceApplication application;
    private final Map<EntityView, ImageView> drawnPlants = new HashMap<>();
    private final Map<EntityView, ImageView> drawnZombies = new HashMap<>();
    private final Map<EntityView, ImageView> drawnBullets = new HashMap<>();
    private final Map<ImageView, CardView> cards = new HashMap<>();
    private final Dimension2D cellDimension;
    private Optional<ImageView> cardSelected = Optional.empty();
    private boolean isShovelSelected;
    private boolean isFirstZombie = true;
    private boolean isSoundMuted;
    private boolean isOver;

    /**
     * Creates a new {@link GameSceneController}.
     * @param application which this class linked to
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Cannot copy application because the instance must remain the same for future usages"
    )
    public GameSceneController(final FlowerForceApplication application) {
        this.application = application;
        this.application.getController().setGameEngine(this);
        this.rows = this.application.getController().getTotalRows();
        this.cols = this.application.getController().getTotalColumns();
        this.cellDimension = new Dimension2D(YARD_WIDTH / this.cols, YARD_HEIGHT / this.rows);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadCards(final List<CardView> cardViews) {
        final List<ImageView> cardImageViews = new LinkedList<>(List.of(card0, card1, card2, card3, card4,
                card5, card6, card7, card8));
        final List<Label> cardLabels = new LinkedList<>(List.of(lbl0, lbl1, lbl2, lbl3, lbl4, lbl5, lbl6, lbl7, lbl8));
        int i = 0;
        for (; i < cardViews.size() && i < cardImageViews.size() && i < cardLabels.size(); i++) {
            this.cards.put(cardImageViews.get(i), cardViews.get(i));
            //setting images and labels
            cardImageViews.get(i).setImage(cardViews.get(i).getMenuImage());
            cardImageViews.get(i).setDisable(false);
            cardImageViews.get(i).setVisible(true);
            cardLabels.get(i).setText(Integer.toString(cardViews.get(i).getCost()));
            cardLabels.get(i).setVisible(true);
        }
        for (; i < cardImageViews.size() && i < cardLabels.size(); i++) {
            cardImageViews.get(i).setDisable(true);
            cardImageViews.get(i).setVisible(false);
            cardLabels.get(i).setVisible(false);
        }
    }

    private void addBloomEffect() {
        if (this.isShovelSelected) {
            this.imageShovel.setEffect(BLOOM_EFFECT);
        } else {
            this.cardSelected.ifPresent(cIv -> cIv.setEffect(BLOOM_EFFECT));
        }
    }

    private void removeBloomEffect() {
        if (this.isShovelSelected) {
            this.imageShovel.setEffect(null);
            this.isShovelSelected = false;
        } else {
            this.cardSelected.ifPresent(cIv -> cIv.setEffect(null));
            this.cardSelected = Optional.empty();
        }
    }

    @FXML
    void shovelSelected(final MouseEvent event) {
        if (!this.isShovelSelected) {
            this.removeBloomEffect();
            this.isShovelSelected = true;
            this.handleSound(SoundManager::useShovel);
            this.addBloomEffect();
        }
    }

    @FXML
    void selectCard(final MouseEvent event) {
        if (!(this.cardSelected.isPresent() && this.cardSelected.get().equals((ImageView) event.getSource()))) {
            this.removeBloomEffect();
            this.cardSelected = Optional.of((ImageView) (event.getSource()));
            this.handleSound(SoundManager::cardSelected);
            this.addBloomEffect();
        }
    }

    private boolean isInsideYard(final double x, final double y) {
        return x >= YARD_FIRST_X && y >= YARD_FIRST_Y
                && x < YARD_FIRST_X + YARD_WIDTH 
                && y < YARD_FIRST_Y + YARD_HEIGHT;
    }

    @FXML
    void yardClicked(final MouseEvent event) {
        if (isInsideYard(event.getX(), event.getY())) {
            final int row = this.getRow(event.getY());
            final int col = this.getColumn(event.getX());
            if (this.cardSelected.isPresent()
                    && this.application.getController().placePlant(this.cards.get(this.cardSelected.get()), row, col)) {
                this.removeBloomEffect();
                this.handleSound(SoundManager::plantPlaced);
            } else if (this.isShovelSelected && this.application.getController().removePlant(row, col)) {
                this.removeBloomEffect();
                this.handleSound(SoundManager::useShovel);
            }
        } else {
            this.removeBloomEffect();
        }
    }

    @FXML
    void mouseMoved(final MouseEvent event) {
        if ((this.isShovelSelected || this.cardSelected.isPresent()) && isInsideYard(event.getX(), event.getY())) {
            this.coloredCell.relocate(YARD_FIRST_X + getColumn(event.getX()) * this.cellDimension.getWidth(),
                    YARD_FIRST_Y + getRow(event.getY()) * this.cellDimension.getHeight());
            this.coloredCell.setVisible(true);
        } else {
            this.coloredCell.setVisible(false);
        }
    }

    @FXML
    void changeSoundMute() {
        this.isSoundMuted = !this.isSoundMuted; //mute or unmute sounds
    }

    private void handleSound(final Runnable runnable) {
        if (!this.isSoundMuted) {
            runnable.run();
        }
    }

    @FXML
    void selectMenu(final MouseEvent event) {
        if (!isOver) {
            final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm dialog");
            alert.setHeaderText("Are you sure you want to leave?");
            alert.setContentText("Progress of this game will not be saved");
            final Optional<ButtonType> result = alert.showAndWait();
            if (result.get().equals(ButtonType.OK)) {
                this.application.menu();
            }
        } else {
            this.application.menu();
        }
    }

    private int getRow(final double y) {
        return getGridIndex(y - YARD_FIRST_Y, YARD_HEIGHT, this.rows);
    }

    private int getColumn(final double x) {
        return getGridIndex(x - YARD_FIRST_X, YARD_WIDTH, this.cols);
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
        this.updateEntities();
        this.damageEntities();
        this.updateSunCounter();
        this.updateScore();
        this.updateProgressBar();
    }

    private void enableCards() {
        final Set<CardView> enabledCards = this.application.getController().getEnabledCards();
        this.cards.keySet().forEach(cIv -> {
            if (enabledCards.contains(this.cards.get(cIv))) {
                if (cIv.isDisable()) {
                    cIv.setEffect(null);
                    cIv.setDisable(false);
                }
            } else {
                cIv.setEffect(BLACK_WHITE_EFFECT);
                cIv.setDisable(true);
            }
        });
    }

    private void updateEntities() {
        final Set<EntityView> placedPlants = this.application.getController().getPlacedPlants();
        final Set<EntityView> placedZombies = this.application.getController().getPlacedZombies();
        final Set<EntityView> placedBullets = this.application.getController().getPlacedBullets();
        //plants
        if (this.removeEntities(placedPlants, this.drawnPlants)) {
            this.handleSound(SoundManager::zombieHasEaten);
        }
        this.updateEntities(placedPlants, this.drawnPlants);
        if (this.addEntities(placedPlants, this.drawnPlants)) {
            this.handleSound(SoundManager::plantPlaced);
        }
        //zombies
        if (this.removeEntities(placedZombies, this.drawnZombies)) {
            this.handleSound(SoundManager::zombieDied);
        }
        this.updateEntities(placedZombies, this.drawnZombies);
        if (this.addEntities(placedZombies, this.drawnZombies)) {
            if (this.isFirstZombie) {
                this.handleSound(SoundManager::zombiesAreComing);
                this.isFirstZombie = false;
            }
            this.handleSound(SoundManager::zombieGroan);
        }
        //bullets
        if (this.removeEntities(placedBullets, this.drawnBullets)) {
            this.handleSound(SoundManager::bulletHit);
        }
        this.updateEntities(placedBullets, this.drawnBullets);
        if (this.addEntities(placedBullets, this.drawnBullets)) {
            this.handleSound(SoundManager::bulletShot);
        }

    }

    private boolean removeEntities(final Set<EntityView> newEntities, final Map<EntityView, ImageView> drawnEntities) {
        final Set<EntityView> toRemove =  drawnEntities.keySet().stream()
                .filter(e -> !newEntities.contains(e))
                .collect(Collectors.toSet());
        toRemove.forEach(e -> {
            this.gamePane.getChildren().remove(drawnEntities.get(e));
            drawnEntities.remove(e);
        });
        return !toRemove.isEmpty();
    }

    private void updateEntities(final Set<EntityView> newEntities, final Map<EntityView, ImageView> drawnEntities) {
        newEntities.stream()
                .filter(e -> drawnEntities.containsKey(e))
                .forEach(e -> {
                    drawnEntities.get(e).relocate(e.getPlacingPosition().getX() + YARD_FIRST_X,
                        e.getPlacingPosition().getY() + YARD_FIRST_Y);
                    drawnEntities.get(e).setImage(e.getPlaceableImage());
                });
    }

    private boolean addEntities(final Set<EntityView> newEntities, final Map<EntityView, ImageView> drawnEntities) {
        return newEntities.stream()
                    .filter(e -> !drawnEntities.containsKey(e))
                    .peek(e -> {
                        final ImageView iv = toImageView(e.getPlaceableImage(), e.getPlacingPosition());
                        drawnEntities.put(e, iv);
                        this.gamePane.getChildren().add(iv);
                    })
                    .findAny().isPresent();
    }

    private void damageEntities() {
        final Set<EntityView> damagedEntities = this.application.getController().getDamagedEntities();
        //plants
        if (this.damageDrawnEntities(damagedEntities, this.drawnPlants)) {
            this.handleSound(SoundManager::zombieEating);
        }
        //zombies
        if (this.damageDrawnEntities(damagedEntities, this.drawnZombies)) {
            this.handleSound(SoundManager::bulletHit);
        }
    }

    private boolean damageDrawnEntities(final Set<EntityView> damagedEntities, final Map<EntityView, ImageView> drawnEntities) {
        return drawnEntities.keySet().stream()
                .peek(e -> drawnEntities.get(e).setEffect(null))
                .filter(e -> damagedEntities.contains(e))
                .peek(e -> drawnEntities.get(e).setEffect(DAMAGE_EFFECT))
                .findAny().isPresent();
    }

    private void updateSunCounter() {
        final int newSunCounter = this.application.getController().getSunCounter();
        if (newSunCounter != Integer.parseInt(this.lblSunCounter.getText())) {
            this.lblSunCounter.setText(Integer.toString(newSunCounter));
        }
    }

    private void updateScore() {
        final int newScore = this.application.getController().getScore();
        if (newScore != Integer.parseInt(this.lblScore.getText())) {
            this.lblScore.setText(Integer.toString(newScore));
        }
    }

    private void updateProgressBar() {
        final double newPercentage = this.application.getController().getProgressState();
        if (newPercentage != this.progressBar.getProgress()) {
            this.progressBar.setProgress(newPercentage);
        }
    }

    private ImageView toImageView(final Image image, final Point2D pos) {
        final ImageView iv = new ImageView(image);
        iv.relocate(YARD_FIRST_X + pos.getX(), YARD_FIRST_Y + pos.getY());
        iv.setPreserveRatio(true);
        iv.setFitWidth(image.getWidth());
        iv.setFitHeight(image.getHeight());
        iv.setMouseTransparent(true);
        return iv;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dimension2D getYardDimension() {
        return new Dimension2D(YARD_WIDTH, YARD_HEIGHT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void over(final boolean isWon) {
        this.isOver = true;
        this.resultPane.setVisible(true);
        this.resultPane.setDisable(false);
        this.resultPane.toFront();
        this.cards.keySet().forEach(c -> c.setDisable(true));
        this.imageShovel.setDisable(true);
        this.removeBloomEffect();
        this.cards.keySet().forEach(card -> card.setDisable(true));
        if (isWon) {
            imageResult.setImage(new Image(ResourceFinder.getCommonImageURL("victory.png").toExternalForm()));
        } else {
            imageResult.setImage(new Image(ResourceFinder.getCommonImageURL("loss.png").toExternalForm()));
        }
    }
}
