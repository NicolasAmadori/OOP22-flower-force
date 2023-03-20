package flowerforce.model.game;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;

/**
 * Models the yard of the game, in which the entities are placed.
 */
public final class Yard {

    private static final int ROWS = 5;
    private static final int COLS = 9;
    private static final int HEIGHT = 880;
    private static final int WIDTH = 1314;
    private static final Dimension2D YARD = new Dimension2D(WIDTH, HEIGHT);
    private static final Dimension2D CELL = new Dimension2D((int) (WIDTH / COLS), (int) (HEIGHT / ROWS));;

    private Yard() {
    }

    private static Point2D getLeftUpCorner(int r, int c) {
        return new Point2D(r * CELL.getWidth(), c * CELL.getHeight());
    }

    /**
     * @param row of the cell
     * @param col of the cell
     * @return the point on the right side of the cell, vertically centered
     */
    public static Point2D getRightEntityPosition(int row, int col) {
        return getLeftUpCorner(row, col).add(CELL.getWidth(), (int) (CELL.getHeight() / 2));
    }

    /**
     * 
     * @return the dimension of a single cell
     */
    public static Dimension2D getCellDimension() {
        return CELL;
    }

    /**
     * 
     * @return the dimension of the entire yard
     */
    public static Dimension2D getYardDimension() {
        return YARD;
    }

    /**
     * 
     * @return the rows number of yard's cells matrix
     */
    public static int getRowsNum() {
        return ROWS;
    }

    /**
     *
     * @return the columns number of yard's cells matrix
     */
    public static int getColsNum() {
        return COLS;
    }

}
