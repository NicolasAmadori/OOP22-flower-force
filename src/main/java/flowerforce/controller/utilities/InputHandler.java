package flowerforce.controller.utilities;

import javafx.util.Pair;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

public class InputHandler implements Cloneable{


    private Set<Pair<Integer,Pair<Integer, Integer>>> placedCell = new HashSet<>();
    private Set<Pair<Integer, Integer>> removedCell = new HashSet<>();
    private Optional<Integer> clickedCardId = Optional.empty();
    public InputHandler() {

    }

    public void placeCell(final int row, final int col) {
        if(clickedCardId.isPresent()) {
            placedCell.add(new Pair<>(clickedCardId.get(),new Pair<>(row, col)));
            clickedCardId = Optional.empty();
        }
    }

    public Pair<Integer,Pair<Integer, Integer>> getNextPlacedCell() {
        if(this.hasNextPlacedCell()) {
            Pair<Integer,Pair<Integer, Integer>> cell = this.placedCell.stream().findAny().get();
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
        if(this.hasNextRemovedCell()) {
            Pair<Integer, Integer> cell = this.removedCell.stream().findAny().get();
            this.removedCell.remove(cell);
            return cell;
        }
        throw new NoSuchElementException("There is no cell removed, call hasNextRemovedCell before invoking this method");
    }

    public boolean hasNextRemovedCell() {
        return this.removedCell.size() > 0;
    }
    public void clickCard(final int id) {
        this.clickedCardId = Optional.of(id);
    }
}
