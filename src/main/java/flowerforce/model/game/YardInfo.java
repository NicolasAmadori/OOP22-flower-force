package flowerforce.model.game;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;

/**
 * Models the yard of the game, in which the entities are placed.
 */
public final class YardInfo {

    private static final int ROWS = 5;
    private static final int COLS = 9;
    private static final double HEIGHT = 880.0;
    private static final double WIDTH = 1314.0;
    private static final Dimension2D YARD = new Dimension2D(WIDTH, HEIGHT);
    private static final Dimension2D CELL = new Dimension2D(WIDTH / COLS, HEIGHT / ROWS);

    private YardInfo() {
    }

    /**
     * @param row of the cell
     * @param col of the cell
     * @return the lower right corner of the cell, where the entity will be placed
     */
    public static Point2D getEntityPosition(final int row, final int col) {
        return new Point2D((col + 1) * CELL.getWidth(), (row + 1) * CELL.getHeight());
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

    /**
     * 
     * @param center point, that is the bottom right corner of the central cell of the square
     * @param radius of the square, in number of cells 
     * @return the top left corner of this square
     */
    public static Point2D toTopLeftCorner(final Point2D center, final int radius) {
        return new Point2D(center.getX() - (radius + 1) * CELL.getWidth(), center.getY() - (radius + 1) * CELL.getHeight());
    }

    /**
     * 
     * @param center point, that is the bottom right corner of the central cell of the square
     * @param radius of the square, in number of cells 
     * @return the bottom right corner of this square
     */
    public static Point2D toBottomRightCorner(final Point2D center, final int radius) {
        return new Point2D(center.getX() + radius * CELL.getWidth(), center.getY() + radius * CELL.getHeight());
    }

}
