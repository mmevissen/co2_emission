package agents;

import java.util.ArrayList;
import java.util.List;

import cellularmodel.Cell;
import cellularmodel.Edge;
import cellularmodel.Node;
import cellularmodel.TrafficLight;
import simulation.Timer;

public class Vehicle implements Agent {

	// parameter:
	// * == constant

	private boolean expired = false;

	private int id;

	/**
	 * fuel type (gasoline, diesel, lpg-gas) *
	 */
	private FuelType fuelType;

	/**
	 * size [int]: Number of raster cells *
	 */
	private int size;

	/**
	 * number of people [int (1..n)] *
	 */
	private int numberOfPeople;

	/**
	 * weight empty [kg] *
	 */
	private double emptyWeight;

	/**
	 * weight total [kg]
	 */
	private double totalWeight;

	/**
	 * TODO: average consumption <-- Formeln random
	 * 
	 */
	private double avgConsumption;

	/**
	 * driving direction
	 */
	private Direction drivingDirection;

	/**
	 * next driving direction
	 */
	private Direction nextDrivingDirection;

	/**
	 * 
	 */
	private Cell currentCell;

	private Edge nextEdge;

	// velocity [m/s]
	private int velocity;

	private int maxVelocity;

	// acceleration [m/s^2]

	// constructor
	public Vehicle(int id, Cell currentCell) {
		super();
		this.id = id;
		this.velocity = 0;
		this.maxVelocity = 5;
		this.currentCell = currentCell;
		currentCell.setVehicle(this);
	}

	public Vehicle(int id, Cell currentCell, int velocity) {
		super();
		this.id = id;
		this.velocity = velocity;
		this.maxVelocity = 5;
		this.currentCell = currentCell;
		currentCell.setVehicle(this);
	}

	// methods
	@Override
	public boolean update() {
		if (expired)
			return false;

		moveForward(this.velocity);
		return true;
	}

	@Override
	public boolean calculate() {
		try {
			accelerate();
			// System.out.println("velocityAcc: " + this.velocity);
			// System.out.println(this.getId() + " " +
			// brake(distanceToNextVehicle()));
			brake(distanceToNextVehicle());
			// System.out.println("velocityBrake: " + this.velocity);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 */
	private void accelerate() {
		if (this.velocity < this.maxVelocity) {
			this.velocity++;
		}
	}

	/**
	 * 
	 */
	private boolean brake(int distToNextVehicle) {
		if (distToNextVehicle < this.velocity) {
			this.velocity = distToNextVehicle;
			return true;
		}
		return false;
	}

	/**
	 * cell distance to next vehicle (if 1 is returned, then there is a vehicle
	 * directly on the next cell)
	 */
	private int distanceToNextVehicle() {
		int i = 0;
		Cell nextCell = this.currentCell.getNextCell();

		for (; i < velocity; i++) {
			// System.out.println(this.currentCell.getLane().getEndNode().getClass());

			if (nextCell != null) {
				if (nextCell.getVehicle() != null) {
					break;
				}
				nextCell = nextCell.getNextCell();

			} else if (this.currentCell.getLane().getEndNode().getClass() == TrafficLight.class) {

				if (this.currentCell.getLane().getEdge() == ((TrafficLight) this.currentCell.getLane().getEndNode())
						.getGoEdge()) {
					calculateNextEdge();

					System.out.println("next Edge: " + nextEdge.getName());
					nextCell = this.nextEdge.getLanes().get(1).getHead();

					if (nextCell.getVehicle() != null) {
						break;
					}
					continue;
				}
			} else if (this.currentCell.getLane().getEndNode().getClass() == Node.class) {
				this.expire();
			}
		}
		return i;

		// return nextCell(this.currentCell) - 1;

	}

	private void expire() {
		if (!Timer.getExpiredAgents().contains(this)) {
			System.out.println("expire: " + this.id);
			this.currentCell.setVehicle(null);

			Timer.getExpiredAgents().add(this);
		}
		this.expired = true;
	}

	private int nextCell(Cell cell) {
		int i = 1;
		try {
			Cell nextCell = cell.getNextCell();

			// System.out.println(String.format("current: %s | next: %s",
			// cell.id, nextCell.id));

			if (nextCell != null) {
				if (nextCell.getVehicle() == null) {
					i += nextCell(nextCell);
				}
			} else if (calculateNextEdge().getEndNode().getClass() == TrafficLight.class) {
				System.out.println("next Edge: " + nextEdge.getName());
				nextCell = this.nextEdge.getLanes().get(1).getHead();
				if (nextCell.getVehicle() == null) {
					i += nextCell(nextCell);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return i;
	}

	// TODO repair
	private void moveForward(int i) {
		Cell nextCell;
		for (int s = 0; s < i; s++) {
			nextCell = this.currentCell.getNextCell();
			if (nextCell != null) {
				this.currentCell.setVehicle(null);
				this.currentCell = nextCell;
				nextCell.setVehicle(this);
			} else {
				if (this.nextEdge != null) {
					Cell newCell = this.nextEdge.getLanes().get(1).getHead();

					this.currentCell.setVehicle(null);
					this.currentCell = newCell;
					newCell.setVehicle(this);
					this.nextEdge = null;
				}
			}
		}
	}

	private Edge calculateNextEdge() {
		try {
			Edge edge = this.currentCell.getLane().getEdge();
			Node node = edge.getEndNode();

			if (node.getClass() == TrafficLight.class) {
				List<Edge> edges = new ArrayList<Edge>();
				edges.addAll(node.getEdges());
				System.out.println("edges: " + edges.size());
				edges.remove(edge);

				if (edges.size() == 0) {
					nextEdge = null;

				} else {
					this.nextEdge = edges.get((int) (Math.random() * edges.size()));
				}
				return this.nextEdge;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public int getId() {
		return id;
	}

	/**
	 * random direction at junction
	 */

}
