package cellularmodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Lane {

	private List<Cell> cells = new ArrayList<Cell>();

	private Edge edge;

	private boolean invert;

	public List<Cell> getCells() {
		return cells;
	}

	public Lane(int numberOfCells, boolean invert, Edge edge) {
		this.edge = edge;
		this.invert = invert;

		createCells(numberOfCells);

		if (invert == true) {
			this.reverse();
		}
	}

	public Node getStartNode() {
		if (this.invert) {
			return edge.getEndNode();
		}
		return edge.getStartNode();
	}

	public Node getEndNode() {
		if (this.invert) {
			return edge.getStartNode();
		}
		return edge.getEndNode();
	}

	public Edge getEdge() {
		return edge;
	}

	public Cell getHead() {
		return cells.get(0);
	}

	public Cell getTail() {
		return cells.get(cells.size() - 1);
	}

	private void createCells(int numberOfCells) {
		if (cells.isEmpty()) {
			cells.add(new Cell(cells.size(), this));
		}

		for (int i = 1; i < numberOfCells; i++) {
			// System.out.println("new Cell: " + i);

			Cell newCell = new Cell(i, this);
			newCell.setPreviousCell(cells.get(i - 1));
			cells.add(newCell);
			cells.get(i - 1).setNextCell(newCell);
		}
		System.out.println("# of cells: " + cells.size());
	}

	public void reverse() {
		Collections.reverse(this.cells);
		for (Cell cell : cells) {
			if (cell.getClass() == Cell.class) {
				((Cell) cell).flip();
			}

		}
	}

	@Override
	public String toString() {
		String s = "";

		for (Cell cell : cells) {
			if (cell.getVehicle() != null) {
				s += " " + cell.getVehicle().getId() + " ";
			} else {
				s += " - ";
			}
		}
		return s;
	}
}
