package flowerforce.controller;

import java.awt.*;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Node;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * {@inheritDoc}.
 */
public class GameControllerImpl implements GameController, Initializable {

    @FXML
    private AnchorPane gamePane;

    @FXML
    private ImageView imgBackground;

    @FXML
    private GridPane griglia;

    @FXML
    private Button btnSunflower;

    @FXML
    private Button btnPeashooter;

    @FXML
    private Label lblSunCounter;

    @FXML
    void selectPeashooter(ActionEvent event) {
        System.out.println("Peashooter selected");
    }

    @FXML
    void selectSunflower(ActionEvent event) {
        System.out.println("Sunflower selected");
    }

    @FXML
    void cellClicked(MouseEvent event) {
        Node source = (Node) event.getSource();
        System.out.println(griglia.getColumnIndex(source) + " " + griglia.getRowIndex(source));

        ImageView newPlant = new ImageView(new Image("flowerforce/icon.png"));
        newPlant.setOnMouseClicked(this::cellClicked);

        griglia.add(newPlant, griglia.getColumnIndex(source), griglia.getRowIndex(source));
        griglia.getChildren().remove(source);
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
        setWindowSize();
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

    private void setWindowSize() {
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //imgBackground.setFitHeight(screenSize.getHeight());
        //imgBackground.setFitWidth(screenSize.getWidth());
        //gamePane.setPrefWidth(screenSize.getWidth());
        //gamePane.setPrefHeight(screenSize.getHeight());
    }
}
