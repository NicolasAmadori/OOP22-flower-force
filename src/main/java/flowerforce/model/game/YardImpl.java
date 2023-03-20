package flowerforce.model.game;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;

/**
 * This is the implementation of {@link Yard}.
 */
public class YardImpl implements Yard {

    private static final int ROWS = 5;
    private static final int COLS = 9;
    private static final int HEIGHT = 880;
    private static final int WIDTH = 1314;
    private final Dimension2D yardDimension;
    private final Dimension2D cellDimension;

    /**
     * Implements a yard.
     */
    public YardImpl() {
        this.cellDimension = new Dimension2D((int) (WIDTH / COLS), (int) (HEIGHT / ROWS));
        this.yardDimension = new Dimension2D(WIDTH, HEIGHT);
    }

    private Point2D getLeftUpCorner(int r, int c) {
        return new Point2D(r * this.cellDimension.getWidth(), c * this.cellDimension.getHeight());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point2D getRightEntityPosition(int row, int col) {
        return getLeftUpCorner(row, col).add(this.cellDimension.getWidth(), (int) (this.cellDimension.getHeight() / 2));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dimension2D getCellDimension() {
        return this.cellDimension;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dimension2D getYardDimension() {
        return this.yardDimension;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRowsNum() {
        return ROWS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getColsNum() {
        return COLS;
    }

}
