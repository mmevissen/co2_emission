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
	
	public VehicleBuilder(double propability, List<Edge> edges) {
		super();
		this.propability = propability;
//		this.edges = edges;
		this.cells = getStartCells(edges);
	}

	
	public List<Vehicle> generateVehicle(){
		List<Vehicle> vehicles = new ArrayList<Vehicle>();
		for (Cell cell : cells) {
			if(cell.getVehicle() == null){
				if (Math.random() <= propability) {
					vehicles.add(new Vehicle(i++, cell));
				}
			}
		}
		return vehicles;
	}

	private List<Cell> getStartCells(List<Edge> edges){
		List<Cell> cells = new ArrayList<Cell>();
		for (Edge edge : edges) {
			cells.add(edge.getLanes().get(0).getHead());
		}
		return cells;
	}
}
