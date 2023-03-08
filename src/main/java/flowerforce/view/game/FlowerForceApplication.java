package flowerforce.view.game;

import flowerforce.controller.GameController;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This is an implementation of {@link GameView}.
 */
public final class FlowerForceApplication extends Application implements FlowerForceView {

    //private final GameController controller = new GameControllerImpl();
    private Stage stage;

    @Override
    public void start(final Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        //TODO: setStageSize()
        //this.stage.setFullScreen(true);
        this.stage.setResizable(false);
        this.stage.setTitle("Flower Force");
        this.stage.getIcons().add(new Image("flowerforce/icon.png"));
        this.menu();        
        this.stage.show();
    }

    @Override
    public void menu() {
        //TODO: open MenuScene
        this.game();
    }

    @Override
    public void game() {
        try {
            FlowerForceScene sceneClass = new GameScene(this);
            this.setScene(sceneClass.getScene());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void setScene(final Scene scene) {
        this.stage.setScene(scene);
        this.stage.show();
    }
}
