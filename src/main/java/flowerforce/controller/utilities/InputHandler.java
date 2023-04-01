package flowerforce.controller.utilities;

import javafx.util.Pair;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * A class that receive and store input commands to return them later.
 * The purpose of this class is to receive inputs from the view, and then return it to the controller to update the model.
 */
public final class InputHandler {
    private Set<Pair<Integer, Pair<Integer, Integer>>> placedCell = new HashSet<>();
    private Set<Pair<Integer, Integer>> removedCell = new HashSet<>();
    public InputHandler() {

    }

    /**
     * Get a clone of the input handler.
     * @param inputHandler The input handler instance to clone
     */
    public InputHandler(final InputHandler inputHandler) {
        this.placedCell = inputHandler.placedCell;
        this.removedCell = inputHandler.removedCell;
    }

    /**
     * Save a placing cell input
     * @param plantId the id of the plant.
     * @param row the row to place the plant in
     * @param col the column to place the plant in
     */
    public void placeCell(final int plantId, final int row, final int col) {
        placedCell.add(new Pair<>(plantId, new Pair<>(row, col)));
    }

    /**
     * Get a place cell input information.
     * @return a Pair
     */
    public Pair<Integer, Pair<Integer, Integer>> getNextPlacedCell() {
        if (this.hasNextPlacedCell()) {
            Pair<Integer, Pair<Integer, Integer>> cell = this.placedCell.stream().findAny().get();
            this.placedCell.remove(cell);
            return cell;
        }
        throw new NoSuchElementException("There is no cell placed, call hasNextPlacedCell before invoking this method");
    }

    public boolean hasNextPlacedCell() {
        return this.placedCell.size() > 0;
    }

    public void removeCell(final int row, final int col) {
        removedCell.add(new Pair<>(row, col));
    }

    public Pair<Integer, Integer> getNextRemovedCell() {
        if (this.hasNextRemovedCell()) {
            Pair<Integer, Integer> cell = this.removedCell.stream().findAny().get();
            this.removedCell.remove(cell);
            return cell;
        }
        throw new NoSuchElementException("There is no cell removed, call hasNextRemovedCell before invoking this method");
    }

    public boolean hasNextRemovedCell() {
        return this.removedCell.size() > 0;
    }
}
