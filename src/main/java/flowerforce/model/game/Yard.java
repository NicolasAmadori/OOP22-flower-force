package flowerforce.model.game;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;

/**
 * Models the yard of the game, in which entities can move.
 */
public interface Yard {

    /**
     * @param row of the cell
     * @param col of the cell
     * @return the point on the right side of the cell, vertically centered
     */
    Point2D getRightEntityPosition(int row, int col);

    /**
     * 
     * @return the cell dimensions
     */
    Dimension2D getCellDimension();

    /**
     * 
     * @return the dimension of the entire yard
     */
    Dimension2D getYardDimension();

}
