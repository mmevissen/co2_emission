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

    public int getMaxNumberOfVehicles() {
        return maxNumberOfVehicles;
    }

    public int getNumberOfGeneratedVehicles() {
        return numberOfGeneratedVehicles;
    }

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
                if (Math.random() <= propability) {
                    if(maxNumberOfVehicles < 0){
                        GenerateVehicle(vehicles, cell);
                    }else{
                        if( numberOfGeneratedVehicles < maxNumberOfVehicles){
                            GenerateVehicle(vehicles, cell);
                        }
                    }
                }
            }
        }

        return vehicles;
    }

    private void GenerateVehicle(List<Vehicle> vehicles, Cell cell) {
        vehicles.add(new Vehicle(i++, cell));
        numberOfGeneratedVehicles++;
    }

    private List<Cell> getStartCells(List<Edge> edges) {
        List<Cell> cells = new ArrayList<Cell>();
        for (Edge edge : edges) {
            cells.add(edge.getLanes().get(0).getHead());
        }
        return cells;
    }
}
