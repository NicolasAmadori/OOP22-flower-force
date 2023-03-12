package flowerforce.view.game;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import flowerforce.view.entities.EntityTypeView;
import flowerforce.view.entities.EntityView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Dimension2D;
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

    //Garden size: 1920x1080, yard size: 1320x880. Down-shift: 150px, right-shift: 600px.
    private static final double RIGHTSHIFT_RATIO = 600.0 / 1920.0;
    private static final double DOWNSHIFT_RATIO = 150.0 / 1080.0;
    private static final double WIDTH_RATIO = 1320.0 / 1920.0;
    private static final double HEIGHT_RATIO = 880.0 / 1080.0;
    private static final double IMAGE_RATIO_WIDTH = 2 / 1920.0; //TODO: resize must be equal to all images?
    private static final double IMAGE_RATIO_HEIGHT = 2 / 1080.0;

    private final FlowerForceApplication application;
    private final Dimension2D size;
    private final Set<EntityView> entities = new HashSet<>();
    private final Point2D firstYardPoint;
    private final Dimension2D yardDimension;
    private final Dimension2D imageRatio;

    public GameSceneController(final FlowerForceApplication application, final Dimension2D size) {
        this.application = application;
        this.size = size;
        this.firstYardPoint = new Point2D((int) (size.getWidth() * RIGHTSHIFT_RATIO), (int) (size.getHeight() * DOWNSHIFT_RATIO));
        this.yardDimension = new Dimension2D((int) (size.getWidth() * WIDTH_RATIO), (int) (size.getHeight() * HEIGHT_RATIO));
        System.out.println(this.firstYardPoint + " " + this.yardDimension); //TODO: remove
        this.imageRatio = new Dimension2D(size.getWidth() * IMAGE_RATIO_WIDTH, size.getHeight() * IMAGE_RATIO_HEIGHT);
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
        cnvYard.setWidth(this.yardDimension.getWidth());
        cnvYard.setHeight(this.yardDimension.getHeight());
        cnvYard.setTranslateX(this.firstYardPoint.getX());
        cnvYard.setTranslateY(this.firstYardPoint.getY());
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
        entities.forEach(e -> this.draw(e.getEntityType().getImage(), e.getPlacingPosition()));
    }

    private void clearCanvas() {
        GraphicsContext gc = this.cnvYard.getGraphicsContext2D();
        gc.clearRect(0, 0, this.cnvYard.getWidth(), this.cnvYard.getHeight());
    }

    private void draw(final Image image, final Point2D pos) {
        GraphicsContext gc = this.cnvYard.getGraphicsContext2D();
        //TODO: must resize correctly the image, depending on screen size
        gc.drawImage(image, 0, 0, image.getWidth(), image.getHeight(),
                this.firstYardPoint.getX() + pos.getX(), this.firstYardPoint.getY() + pos.getY(), image.getWidth() * this.imageRatio.getWidth(), image.getHeight() * this.imageRatio.getHeight());
        ImageView iv = new ImageView(image);
        iv.relocate(this.firstYardPoint.getX() + pos.getX(), this.firstYardPoint.getY() + pos.getY());
        iv.setPreserveRatio(true);
        iv.setFitWidth(image.getWidth() * this.imageRatio.getWidth());
        iv.setFitHeight(image.getHeight() * this.imageRatio.getHeight());
        this.gamePane.getChildren().add(iv);
    }

    @Override
    public Dimension2D getFieldSize() {
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