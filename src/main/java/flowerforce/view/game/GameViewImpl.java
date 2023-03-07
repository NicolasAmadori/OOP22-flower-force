package flowerforce.view.game;

import java.util.HashSet;
import java.util.Set;

import flowerforce.view.entities.EntityView;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This is an implementation of {@link GameView}.
 */
public final class GameViewImpl extends Application implements GameView  {

    private final Set<EntityView> entities = new HashSet<>();

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("flowerforce/game/Garden.fxml"));
        final Scene scene = new Scene(root);

        primaryStage.setTitle("Flower Force - Level 1");
        primaryStage.getIcons().add(new Image("flowerforce/icon.png"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addEntity(EntityView entity) {
        this.entities.add(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeEntity(EntityView entity) {
        this.entities.remove(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearEntities() {
        this.entities.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        
    }
}
