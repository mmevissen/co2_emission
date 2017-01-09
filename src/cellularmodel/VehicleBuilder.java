package cellularmodel;

import java.util.ArrayList;
import java.util.List;

import agents.Vehicle;

public class VehicleBuilder {

    /**
     * 0.0 - 1.0
     */
    private int i = 0;
    private double propability;
    //	private List<Edge> edges;
    private List<Cell> cells;

    private int maxNumberOfVehicles = 0;
    private int numberOfGeneratedVehicles = 0;


    public VehicleBuilder(double propability, List<Edge> edges, int maxNumberOfVehicles) {
        super();
        this.propability = propability;
//		this.edges = edges;
        this.cells = getStartCells(edges);
        this.maxNumberOfVehicles = maxNumberOfVehicles;
    }

    public List<Vehicle> generateVehicle() {
        List<Vehicle> vehicles = new ArrayList<>();
        for (Cell cell : cells) {
            if (cell.getVehicle() == null) {
                if (Math.random() <= propability && numberOfGeneratedVehicles < maxNumberOfVehicles) {
                    vehicles.add(new Vehicle(i++, cell));
                    numberOfGeneratedVehicles++;
                }
            }

        }

        return vehicles;
    }

    private List<Cell> getStartCells(List<Edge> edges) {
        List<Cell> cells = new ArrayList<Cell>();
        for (Edge edge : edges) {
            cells.add(edge.getLanes().get(0).getHead());
        }
        return cells;
    }
}
