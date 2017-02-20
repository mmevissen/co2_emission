package model.agents;

import model.environment.Cell;
import model.environment.Edge;
import model.environment.Node;
import model.simulation.Simulation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Vehicle implements Agent, Serializable {

    //////////// variables ////////////
    private boolean expired = false;

    private FuelType fuelType;
    private int id;

    /**
     * velocity: number of cells the car is currently moving forward
     */
    private int velocity;
    /**
     * the maximum number of cells that a car can move forward
     */
    private int maxVelocity;
    /**
     * cell, where the care is currently located
     */
    private Cell currentCell;
    /**
     * randomly calculated edge where the car will continue its journey. <br/>
     * only used at junction nodes
     */
    private Edge nextEdge;

    //////////// constructors ////////////
    public Vehicle(int id, Cell currentCell, FuelType fuelType) {
        super();
        this.id = id;
        this.velocity = 0;
        this.maxVelocity = 5;
        this.fuelType = fuelType;

        this.currentCell = currentCell;
        this.currentCell.setVehicle(this);
        this.currentCell.increaseCo2Emission(getConsumptionPerCell());
    }

    //////////// methods ////////////

    //////////// getter
    public FuelType getFuelType() {
        return fuelType;
    }

    //////////// setter

    @Override
    public boolean update() {
        if (expired)
            return false;

        moveForward(this.velocity);
        return true;
    }

    @Override
    public boolean calculate(long currentTime) {
        try {
            accelerate();
            brake(distanceToNextVehicle());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private double getConsumptionPerCell() {
        double factor = Fuel.consumptionFactorToGasoline.get(this.fuelType);
        double consumption = Fuel.consumption.get(this.velocity);
        double cellSize = this.currentCell.getSize();
        double co2PerL = Fuel.co2PerL.get(this.fuelType);

        // consumption [l/100km]
        return (consumption * factor / 100000) * cellSize * (co2PerL / 1);
    }

    private void accelerate() {
        if (this.velocity < this.maxVelocity) {
            this.velocity++;
        }
    }

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

        while (i < velocity) {
            // for (; i < velocity; i++) {

            if (nextCell != null) {
                if (nextCell.getVehicle() != null) {
                    break;
                }
                nextCell = nextCell.getNextCell();

            } else if (this.currentCell.getLane().getEndNode().getClass() == TrafficLight.class) {

                if (this.currentCell.getLane().getEdge() == ((TrafficLight) this.currentCell.getLane().getEndNode()).getGoEdge()) {
                    calculateNextEdge();

                    // System.out.println("next Edge: " + nextEdge.getName());
                    nextCell = this.nextEdge.getLanes().get(1).getHead();

                    if (nextCell.getVehicle() != null) {
                        break;
                    }
                    continue;
                } else {
                    return i;
                }
            } else if (this.currentCell.getLane().getEndNode().getClass() == Node.class) {
                this.expire();
            }

            i++;
        }
        return i;

        // return nextCell(this.currentCell) - 1;

    }

    private void expire() {
        if (!Simulation.getExpiredAgents().contains(this)) {
            this.currentCell.setVehicle(null);

            Simulation.getExpiredAgents().add(this);
        }
        this.expired = true;
    }


    private void moveForward(int i) {
        Cell nextCell;
        if (i == 0) {
            this.currentCell.increaseCo2Emission(this.getConsumptionPerCell());
            return;
        }

        for (int s = 0; s < i; s++) {
            nextCell = this.currentCell.getNextCell();
            if (nextCell != null) {
                updateCell(nextCell);
            } else {
                if (this.nextEdge != null) {
                    Cell newCell = this.nextEdge.getLanes().get(1).getHead();

                    updateCell(newCell);

                    this.nextEdge = null;
                } else {
                    this.velocity = 0;
                }
            }
        }
    }

    private void updateCell(Cell newCell) {
        this.currentCell.setVehicle(null);
        this.currentCell = newCell;

        newCell.setVehicle(this);
        newCell.increaseCo2Emission(this.getConsumptionPerCell());
    }

    private Edge calculateNextEdge() {
        try {
            Edge edge = this.currentCell.getLane().getEdge();
            Node node = edge.getEndNode();

            if (node.getClass() == TrafficLight.class) {
                List<Edge> edges = new ArrayList<>();
                edges.addAll(node.getEdges());
                // System.out.println("edges: " + edges.size());
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

    public int getCurrentVelocity() {
        return velocity;
    }
}