package model.environment;

import model.Constants;
import model.agents.Vehicle;

import java.io.Serializable;

/**
 * represents a physical space on which a single car can be placed on
 */
public class Cell implements Serializable {

    // variables
    private int id;

    /**
     * the physical space (length in meters) of this cell. <br/> all cells have the same size!
     */
    private double size = Constants.cellSize;

    /**
     * a car which is placed on this cell. <br/> null if there is no vehicle
     */
    private Vehicle vehicle;

    /**
     * the lane which this cell belongs to
     */
    private Lane lane;

    private Cell nextCell;
    private Cell previousCell;

    /**
     * CO2 emission at this cell caused by passing vehicles
     */
    private double co2Emission;

    // constructor
    public Cell(int id, Lane lane) {
        super();
        this.id = id;
        this.lane = lane;
        this.co2Emission = 0.;
    }

    // getter
    public Lane getLane() {
        return this.lane;
    }

    public Vehicle getVehicle() {
        return this.vehicle;
    }

    public Cell getNextCell() {
        return this.nextCell;
    }

    public Cell getPreviousCell() {
        return this.previousCell;
    }

    public double getCo2Emission() {
        return this.co2Emission;
    }

    public double getSize() {
        return this.size;
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

    /**
     * reverses the next and previous cell. used to invert a lane.
     */
    protected void flip() {
        Cell temp = this.nextCell;
        this.nextCell = this.previousCell;
        this.previousCell = temp;
    }

    /**
     * increases the co2 emission of this cell by a given value
     * @param consumptionPerCell co2 emission value to be added
     */
    public void increaseCo2Emission(double consumptionPerCell) {
        this.co2Emission += consumptionPerCell;
    }
}