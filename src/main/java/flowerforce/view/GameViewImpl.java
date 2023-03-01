package flowerforce.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameViewImpl extends Application implements GameView  {

    public final static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(ClassLoader.getSystemResource("flowerforce/game/Main.fxml"));
        Scene scene = new Scene(root);

        primaryStage.setTitle("Titolo prova");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public int getAllCoins() {
        return 0;
    }
}
