package flowerforce.view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class GameViewImpl extends Application implements GameView  {

    public final static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(ClassLoader.getSystemResource("flowerforce/game/Garden.fxml"));
        Scene scene = new Scene(root);
        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //System.out.println("mouse click detected! " + mouseEvent.getTarget());
            }
        });

        primaryStage.setTitle("Titolo prova");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public int getAllCoins() {
        return 0;
    }
}
