package flowerforce.view.game;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import flowerforce.controller.Controller;
import flowerforce.controller.ControllerImpl;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * This is an implementation of {@link GameView}.
 */
public final class FlowerForceApplication extends Application implements FlowerForceView {

    private static final double SCREEN_FILL_INDEX = 0.8;

    private Controller controller;//The controller of the game

    private static final String GAMEICON_PATH = "flowerforce/icon.png";
    private Stage stage;

    @Override
    public void start(final Stage primaryStage) throws Exception {
        this.stage = primaryStage;
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


        this.stage.setResizable(false);
        this.stage.setTitle("Flower Force");
        this.stage.getIcons().add(new Image(GAMEICON_PATH));
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
            FlowerForceScene sceneClass = new GameScene(this);
            controller.StartNewLevelGame(0);
            this.setScene(sceneClass.getScene());
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

    public static Scene getScaledScene(final AnchorPane root, String imageName) throws IOException {
        final String imageFileName = "flowerforce" + File.separator + "game" + File.separator + "images" + File.separator + imageName;
        final Image image = new Image(imageFileName);
        //dimensioni dell'immagine
        final double imgWidth = image.getWidth();
        final double imgHeight = image.getHeight();
        //prendo le dimensioni dello schermo
        final Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        //calcolo la larghezza dell'applicazione (prendendo come bound sempre la larghezza)
        final double appSizeWidth = SCREEN_FILL_INDEX * screenBounds.getWidth();
        //calcolo la relativa altezza
        final double appSizeHeight = appSizeWidth / imgWidth * imgHeight;
        //calcolo i fattori di scala
        final double scaleFactorWidth = appSizeWidth / imgWidth;
        final double scaleFactorHeight = appSizeHeight / imgHeight;
        final Scale scaleTransformation = new Scale(scaleFactorWidth, scaleFactorHeight, 0, 0);
        root.getTransforms().add(scaleTransformation);
        return new Scene(root, appSizeWidth, appSizeHeight);
    }
}
