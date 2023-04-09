package flowerforce.view.game;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import flowerforce.common.ResourceFinder;
import flowerforce.controller.Controller;
import flowerforce.controller.ControllerImpl;
import flowerforce.view.utilities.SoundManager;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Bounds;
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

    // percentage of screen's width or height to be occupied by the application
    private static final double SCREEN_FILL_INDEX = 0.8;

    private Controller controller;

    private static final String GAMEICON_NAME = "icon.png";
    private Stage stage;

    private AnimationTimer gameLoop;

    @SuppressFBWarnings(
        value = "EI2",
        justification = "the showed stage is saved as a field to change its scene"
    )
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
        this.stage.setTitle("Flower Force");
    }

    @Override
    public void adventureModeGame(final int levelId) {
        this.controller.startNewAdventureModelGame(levelId);
        this.game("Level " + levelId);
    }

    @Override
    public void survivalModeGame() {
        this.controller.startNewSurvivalModeGame();
        this.game("Survival Mode");
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

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification = "we need to interact with always the same controller instance"
    )
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
     * @param root the root element of the scene
     * @return a scaled scene based on screen's dimensions
     */
    public static Scene getScaledScene(final AnchorPane root) {
        // app's dimensions
        final Dimension2D appDimensions = getAppDimensionFromRootBounds(root.getBoundsInLocal());
        // calculation of scale factors
        final double scaleFactorWidth = appDimensions.getWidth() / root.getBoundsInLocal().getWidth();
        final double scaleFactorHeight = appDimensions.getHeight() / root.getBoundsInLocal().getHeight();
        final Scale scaleTransformation = new Scale(scaleFactorWidth, scaleFactorHeight);
        root.getTransforms().add(scaleTransformation);
        return new Scene(root, appDimensions.getWidth(), appDimensions.getHeight());
    }

    /*
     * Returns the application's dimensions based on root node's bounds.
     */
    private static Dimension2D getAppDimensionFromRootBounds(final Bounds rootBounds) {
        // screen's dimensions
        final Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        // calculation of app's width
        double appSizeWidth = SCREEN_FILL_INDEX * screenBounds.getWidth();
        // calculation of app's height
        double appSizeHeight = appSizeWidth / rootBounds.getWidth() * rootBounds.getHeight();
        // case where app's height would be greater than screen's height
        if (appSizeHeight > screenBounds.getHeight()) {
            appSizeHeight = screenBounds.getHeight();
            appSizeWidth = appSizeHeight / rootBounds.getHeight() * rootBounds.getWidth();
        }
        return new Dimension2D(appSizeWidth, appSizeHeight);
    }

}
