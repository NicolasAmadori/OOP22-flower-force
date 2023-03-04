package flowerforce.controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;


/**
 * {@inheritDoc}.
 */
public class GameControllerImpl implements GameController, Initializable {

    @FXML
    private AnchorPane gamePane;

    @FXML
    private Button btnProva;

    @FXML
    private ImageView imgBackground;

    @FXML
    private GridPane griglia;

    @FXML
    void entered(MouseEvent event) {
        System.out.println("ARGH");
    }

    @FXML
    void regionClicked(ActionEvent event) {
        System.out.println("ARGH");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPlayerCoins() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPlayerCoins'");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Integer, Boolean> getLevelIds() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLevelIds'");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startLevelGame(final int levelId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'startLevelGame'");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startInfiniteGame() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'startInfiniteGame'");
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //setWindowSize();

        griglia.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                Node source = (Node) event.getTarget();
                System.out.println(griglia.getColumnIndex(source) + " " + griglia.getRowIndex(source));
            }
        });
    }

    private void setWindowSize() {
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        gamePane.setPrefWidth(screenSize.getWidth() - 200);
        gamePane.setPrefHeight(screenSize.getHeight() - 200);
        imgBackground.setFitWidth(gamePane.getPrefWidth());
        imgBackground.setFitHeight(gamePane.getPrefHeight());
    }
}
