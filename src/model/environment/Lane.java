package model.environment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * a chain of {@link Cell}s which represents a lane of a road
 */
public class Lane implements Serializable {

    private boolean inverted;

    /**
     * "oriented" list of cells. the head of this list corresponds to the entry of this lane.<br/>
     * each cell knows its previous and next cell
     */
    private List<Cell> cells = new ArrayList<>();

    /**
     * the edge this lane belongs to
     */
    private Edge edge;

    // constructor
    public Lane(int numberOfCells, boolean invert, Edge edge) {
        this.edge = edge;
        this.inverted = invert;

        createCells(numberOfCells);

        if (invert == true) {
            this.reverse();
        }
    }

    // getter
    public Node getStartNode() {
        if (this.inverted) {
            return this.edge.getEndNode();
        }
        return this.edge.getStartNode();
    }

    public Node getEndNode() {
        if (this.inverted) {
            return this.edge.getStartNode();
        }
        return this.edge.getEndNode();
    }

    public Edge getEdge() {
        return this.edge;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public Cell getHead() {
        return this.cells.get(0);
    }

    public Cell getTail() {
        return this.cells.get(this.cells.size() - 1);
    }

    // methods
    private void createCells(int numberOfCells) {
        if (this.cells.isEmpty()) {
            this.cells.add(new Cell(this.cells.size(), this));
        }

        Cell newCell;
        for (int i = 1; i < numberOfCells; i++) {
            newCell = new Cell(i, this);
            newCell.setPreviousCell(this.cells.get(i - 1));
            this.cells.add(newCell);
            this.cells.get(i - 1).setNextCell(newCell);
        }
    }

    /**
     * reverses the lane and its cells orientation
     */
    public void reverse() {
        Collections.reverse(this.cells);
        for (Cell cell : this.cells) {
            if (cell.getClass() == Cell.class) {
                cell.flip();
            }
        }
    }

    @Override
    public String toString() {
        String s = "";

        for (Cell cell : this.cells) {
            if (cell.getVehicle() != null) {
                s += " " + cell.getVehicle().getId() + "|" + (Math.round(cell.getCo2Emission() * 1000.) / 1000.) + "|" + cell.getVehicle().getCurrentVelocity();
            } else {
                s += " " + (Math.round(cell.getCo2Emission() * 1000.) / 1000.) + " ";
            }
        }
        return s;
    }
}
