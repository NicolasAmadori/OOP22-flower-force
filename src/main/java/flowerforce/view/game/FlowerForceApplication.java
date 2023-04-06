package flowerforce.view.game;

import flowerforce.common.ResourceFinder;
import flowerforce.controller.Controller;
import flowerforce.controller.ControllerImpl;
import flowerforce.view.utilities.SoundManager;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Dimension2D;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
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

    private static final double SCREEN_FILL_INDEX = 0.8;

    private Controller controller;

    private static final String GAMEICON_NAME = "icon.png";
    private Stage stage;

    private AnimationTimer gameLoop;

    @Override
    public void start(final Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        this.controller = new ControllerImpl();
        this.stage.setResizable(false);
        this.stage.getIcons().add(new Image(ResourceFinder.getCommonImagePath(GAMEICON_NAME)));
        this.stage.setOnCloseRequest(e -> {
            Platform.exit();
        });
        this.menu();
        SoundManager.startMainTheme();
    }

    @Override
    public void menu() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
        this.controller.save();
        final FlowerForceScene sceneClass = new MenuScene(this);
        this.setScene(sceneClass.getScene());
    }

    @Override
    public void adventureModeGame(final int levelId) {
        this.controller.startNewAdventureModelGame(levelId);
        this.game("Level " + levelId);
    }

    @Override
    public void survivalModeGame() {
        this.controller.startNewSurvivalModeGame();
        this.game("Adventure mode");
    }

    @Override
    public void howToPlay() {
        final FlowerForceScene sceneClass = new HowToPlayScene(this);
        this.setScene(sceneClass.getScene());
        this.stage.setTitle("HowToPlay");
    }

    @Override
    public void shop() {
        final FlowerForceScene sceneClass = new ShopScene(this);
        this.setScene(sceneClass.getScene());
        this.stage.setTitle("Shop");
        SoundManager.openShop();
    }

    @Override
    public Controller getController() {
        return this.controller;
    }

    private void game(final String title) {
        final FlowerForceScene sceneClass = new GameScene(this);
        this.setScene(sceneClass.getScene());
        this.stage.setTitle(title);
        gameLoop = this.controller.getGameLoop();
        gameLoop.start();
    }

    private void setScene(final Scene scene) {
        this.stage.setScene(scene);
        this.stage.show();
    }

    /**
     * Produces a scene scaled on screen's dimensions.
     * @param root the root element to resize
     * @param imageName the image to take proportions from
     * @return a scaled scene based on screen's dimensions
     */
    public static Scene getScaledScene(final AnchorPane root, final String imageName) {
        //background's dimensions
        final Dimension2D imgDimensions = getImgDimensions(imageName);
        //app's dimensions
        final Dimension2D appDimensions = getAppDimensionFromImage(imgDimensions);
        //calculation of scale factors
        final double scaleFactorWidth = appDimensions.getWidth() / imgDimensions.getWidth();
        final double scaleFactorHeight = appDimensions.getHeight() / imgDimensions.getHeight();
        final Scale scaleTransformation = new Scale(scaleFactorWidth, scaleFactorHeight, 0, 0);
        root.getTransforms().add(scaleTransformation);
        return new Scene(root, appDimensions.getWidth(), appDimensions.getHeight());
    }

    /**
     * Returns the Application dimensions based on a given background image.
     * @param imgDimensions image's dimensions in pixel
     * @return a Dimension2D representing app's dimensions
     */
    public static Dimension2D getAppDimensionFromImage(final Dimension2D imgDimensions) {
        //screen's dimensions
        final Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        //calculation of app's width
        double appSizeWidth = SCREEN_FILL_INDEX * screenBounds.getWidth();
        //calculation of app's height
        double appSizeHeight = appSizeWidth / imgDimensions.getWidth() * imgDimensions.getHeight();
        //case where app's height would be greater than screen's height
        if (appSizeHeight > screenBounds.getHeight()) {
            appSizeHeight = screenBounds.getHeight();
            appSizeWidth = appSizeHeight / imgDimensions.getHeight() * imgDimensions.getWidth();
        }
        return new Dimension2D(appSizeWidth, appSizeHeight);
    }

    /**
     * Gets an image's dimension.
     * @param imgName the image name (located in the standard image folder)
     * @return a Dimension2D contaning image's dimensions
     */
    public static Dimension2D getImgDimensions(final String imgName) {
        final Image image = new Image(ResourceFinder.getCommonImagePath(imgName));
        //image's dimensions
        return new Dimension2D(image.getWidth(), image.getHeight());
    }

}
