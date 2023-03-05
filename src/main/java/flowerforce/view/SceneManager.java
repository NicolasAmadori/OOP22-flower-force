package flowerforce.view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void startLvl1(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("flowerforce/game/TempScene.fxml"));
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.show();
    }
}
