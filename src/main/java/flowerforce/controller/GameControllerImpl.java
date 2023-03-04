package flowerforce.controller;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * {@inheritDoc}.
 */
public class GameControllerImpl implements GameController {

    @FXML
    public void provaClick(ActionEvent e) {
        System.out.println("Click bottone avvenuto");
    }

    @FXML
    public void regionClick(MouseEvent e) throws IOException {
        System.out.println("Click region avvenuto");
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

}
