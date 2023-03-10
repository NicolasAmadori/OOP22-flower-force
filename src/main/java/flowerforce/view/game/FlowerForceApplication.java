package flowerforce.view.game;

import java.awt.Dimension;
import java.awt.Toolkit;
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

    //private final GameController controller = new GameControllerImpl();

    //TODO: use generic separators
    private static final String GAMEICON_PATH = "flowerforce/icon.png";
    private Stage stage;
    private Dimension screenSize;

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
            GameScene sceneClass = new GameScene(this, this.screenSize);
            this.setScene(sceneClass.getScene());

            //TOREMOVE: test
            sceneClass.addEntity(new SunflowerView(new Point2D(100, 100)));
            sceneClass.render();
            
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
