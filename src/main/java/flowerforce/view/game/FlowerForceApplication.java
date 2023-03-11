package flowerforce.view.game;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Optional;

import flowerforce.controller.GameController;
import flowerforce.view.entities.SunflowerView;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This is an implementation of {@link GameEngine}.
 */
public final class FlowerForceApplication extends Application implements FlowerForceView {

    //TODO: use generic separators "/" "\"
    private static final String GAMEICON_PATH = "flowerforce/icon.png";
    private final GameController controller = new GameControllerImpl();
    private Stage stage;
    private Dimension screenSize;
    private FlowerForceScene sceneClass; 

    @Override
    public void start(final Stage primaryStage) throws Exception {
        this.setScreenSize();
        this.stage = primaryStage;
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
            this.sceneClass = new GameScene(this, this.screenSize);
            this.setScene(this.sceneClass.getScene());
            this.controller.setGameEngine(this.sceneClass.getGameEngine().get());

            //TOREMOVE: test
            sceneClass.getGameEngine().ifPresent(ge -> {
                try {
                    ge.addEntity(new SunflowerView(new Point2D(100, 100)));
                    ge.render();
                } catch (Exception e) {
                    System.out.println(e);
                }
            });
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void setScene(final Scene scene) {
        this.stage.setScene(scene);
        this.stage.show();
    }

    private void setScreenSize() {
        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    }
}
