package cellularmodel;

import agents.Vehicle;

import java.io.Serializable;

public class Cell implements Serializable {
    // parameter
    private int id;

    private double size = Constants.cellSize;

    private Lane lane;

    private Vehicle vehicle;

    private Cell nextCell;
    private Cell previousCell;

    private double co2Emission;

    // constructor
    Cell(int id, Lane lane) {
        super();
        this.id = id;
        this.lane = lane;
        co2Emission = 0.;
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

    public double getCo2Emission() {
        return this.co2Emission;
    }

    public double getSize() {
        return size;
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

    public void increaseCo2Emission(double consumptionPerCell) {
        this.co2Emission += consumptionPerCell;
    }

    // methods
    protected void flip() {
        Cell temp = nextCell;
        nextCell = previousCell;
        previousCell = temp;
    }


}
