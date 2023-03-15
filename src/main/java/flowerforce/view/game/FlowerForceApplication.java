package flowerforce.view.game;

<<<<<<< HEAD
import java.io.File;
import java.io.IOException;
=======
import java.awt.Toolkit;
>>>>>>> feat_add-GameView

import flowerforce.controller.Controller;
import javafx.application.Application;
<<<<<<< HEAD
import javafx.geometry.Rectangle2D;
=======
import javafx.geometry.Dimension2D;
>>>>>>> feat_add-GameView
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * This is an implementation of {@link GameEngine}.
 */
public final class FlowerForceApplication extends Application implements FlowerForceView {

<<<<<<< HEAD
    private static final double SCREEN_FILL_INDEX = 0.8;

    private Controller controller;//The controller of the game
=======
    private Controller controller;
>>>>>>> feat_add-GameView

    //TODO: use generic separators "/" "\"
    private static final String GAMEICON_PATH = "flowerforce/icon.png";
    private Stage stage;
    private Dimension2D screenSize;
    private FlowerForceScene sceneClass; 

    @Override
    public void start(final Stage primaryStage) throws Exception {
        this.setScreenSize();
        this.stage = primaryStage;
<<<<<<< HEAD
        this.controller = null;//Instantiate the Controller

        //TODO: setStageSize()
        //this.stage.setFullScreen(true);

        // //dimensioni dell'immagine
        // final double DEFAULT_SIZE_WIDTH = 637;
        // final double DEFAULT_SIZE_HEIGHT = 391;
        // //prendo le dimensioni dello schermo
        // final Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        // //calcolo la larghezza dell'applicazione (prendendo come bound sempre la larghezza)
        // final double menuSizeWidth = 0.8 * screenBounds.getWidth();
        // //calcolo la relativa altezza
        // final double menuSizeHeight = menuSizeWidth / DEFAULT_SIZE_WIDTH * DEFAULT_SIZE_HEIGHT;
        // primaryStage.setWidth(menuSizeWidth);
		// primaryStage.setHeight(menuSizeHeight);


=======
        this.controller = new ControllerImpl();
        this.stage.setFullScreen(true);
>>>>>>> feat_add-GameView
        this.stage.setResizable(false);
        this.stage.setTitle("Flower Force");
        this.stage.getIcons().add(new Image(GAMEICON_PATH));
        this.menu();
        this.menu();
    }

    @Override
    public void menu() {
        try {
            FlowerForceScene sceneClass = new MenuScene(this, controller);
            this.setScene(sceneClass.getScene());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void game() {
        try {
            this.sceneClass = new GameScene(this, this.screenSize);
            this.controller.StartNewLevelGame(0);
            this.setScene(this.sceneClass.getScene());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public Controller getController() {
        return this.controller;
    }

    private void setScene(final Scene scene) {
        this.stage.setScene(scene);
        this.stage.show();
    }

<<<<<<< HEAD
    /**
     * Produces a scene scaled on screen's dimensions.
     * @param root the root element to resize
     * @param imageName the image to take proportions from
     * @return a scaled scene based on screen's dimensions
     * @throws IOException
     */
    public static Scene getScaledScene(final AnchorPane root, final String imageName) throws IOException {
        final String imgPath = "flowerforce" + File.separator + "game" + File.separator + "images" + File.separator + imageName;
        final Image image = new Image(imgPath);
        //background's dimensions
        final double imgWidth = image.getWidth();
        final double imgHeight = image.getHeight();
        //screen's dimensions
        final Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        //calculation of app's width
        double appSizeWidth = SCREEN_FILL_INDEX * screenBounds.getWidth();
        //calculation of app's height
        double appSizeHeight = appSizeWidth / imgWidth * imgHeight;
        //case where app's height would be greater than screen's height
        if (appSizeHeight > screenBounds.getHeight()) {
            appSizeHeight = screenBounds.getHeight();
            appSizeWidth = appSizeHeight / imgHeight * imgWidth;
        }
        //calculation of scale factors
        final double scaleFactorWidth = appSizeWidth / imgWidth;
        final double scaleFactorHeight = appSizeHeight / imgHeight;
        final Scale scaleTransformation = new Scale(scaleFactorWidth, scaleFactorHeight, 0, 0);
        root.getTransforms().add(scaleTransformation);
        return new Scene(root, appSizeWidth, appSizeHeight);
=======
    private void setScreenSize() {
        //TODO: control screen size ratio (16:9)
        this.screenSize = new Dimension2D(Toolkit.getDefaultToolkit().getScreenSize().getWidth(), Toolkit.getDefaultToolkit().getScreenSize().getHeight());
>>>>>>> feat_add-GameView
    }
}
