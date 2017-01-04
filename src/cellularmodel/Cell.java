package cellularmodel;

import agents.Vehicle;

public class Cell {
	
	
	// parameter
	public int id;
	
	private double size = Constants.cellSize;	
	private Vehicle vehicle;

	private Cell nextCell;
	private Cell previousCell;
	
	private Lane lane;
	


	protected double co2Emission = 0;
	

	// constructor
	public Cell(int id, Lane lane) {
		super();
		this.id = id;
		this.lane = lane;
	}

	// getter
	public Lane getLane() {
		return lane;
	}
	
	public Vehicle getVehicle() {
		return vehicle;
	}

	public Cell getNextCell() {
		return nextCell;
	}

	public Cell getPreviousCell() {
		return previousCell;
	}

	// setter
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	public void setNextCell(Cell nextCell) {
		this.nextCell = nextCell;
	}

	public void setPreviousCell(Cell previousCell) {
		this.previousCell = previousCell;
	}

	// methods
	protected void flip() {
		Cell temp = nextCell;
		nextCell = previousCell;
		previousCell = temp;
	}

}
