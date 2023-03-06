package flowerforce.view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SceneManager {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    void start(MouseEvent event) throws IOException {
        System.out.println("Bottone cliccato");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("flowerforce/game/TempScene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
