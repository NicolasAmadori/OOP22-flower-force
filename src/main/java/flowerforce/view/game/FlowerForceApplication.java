package flowerforce.view.game;

import java.awt.Toolkit;

import flowerforce.controller.Controller;
import flowerforce.controller.ControllerImpl;
import javafx.application.Application;
import javafx.geometry.Dimension2D;
import javafx.scene.image.Image;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This is an implementation of {@link GameEngine}.
 */
public final class FlowerForceApplication extends Application implements FlowerForceView {

    private Controller controller;

    //TODO: use generic separators "/" "\"
    private static final String GAMEICON_PATH = "flowerforce/icon.png";
    private Stage stage;
    private FlowerForceScene sceneClass; 

    @Override
    public void start(final Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        this.controller = new ControllerImpl();
        this.stage.setFullScreen(true);
        this.stage.setResizable(false);
        this.stage.setTitle("Flower Force");
        this.stage.getIcons().add(new Image(GAMEICON_PATH));
        this.menu();
    }

    @Override
    public void menu() {
        //TODO: open MenuScene
        this.game();
    }

    @Override
    public void game() {
        try {
            this.sceneClass = new GameScene(this);
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
}
