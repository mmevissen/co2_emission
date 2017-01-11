package agents;

import java.util.*;

import com.sun.media.sound.PortMixerProvider;

import cellularmodel.Cell;
import cellularmodel.Edge;
import cellularmodel.Node;
import cellularmodel.TrafficLight;
import simulation.Timer;

public class Vehicle implements Agent {

    //////////// parameters ////////////
    private boolean expired = false;

    private final FuelType fuelType;
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

    // ********** not yet used **********
    /**
     * size [int]: Number of raster cells
     */
    // private int size;

    /**
     * number of people [int (1..n)]
     */
    // private int numberOfPeople;

    /**
     * weight empty [kg] *
     */
    // private double emptyWeight;

    /**
     * weight total [kg]
     */
    //  private double totalWeight;

    // **********************************


    //////////// constructors ////////////
    public Vehicle(int id, Cell currentCell) {
        super();
        this.id = id;
        this.velocity = 0;
        this.maxVelocity = 5;
        
        double propability = Math.random();
        if (propability <= 0.664){    	
        	this.fuelType = FuelType.Gasoline;
        } else if (propability > 0.664 && propability <= 0.987){
        	this.fuelType = FuelType.Diesel;
        } else if (propability > 0.987 && propability <= 0.998){
        	this.fuelType = FuelType.LPG;
        } else {
        	this.fuelType = FuelType.CNG;
        }
               
        this.currentCell = currentCell;
        this.currentCell.setVehicle(this);
        this.currentCell.increaseCo2Emission(getConsumptionPerCell());
    }

/*    public Vehicle(int id, Cell currentCell, int velocity) {
        super();
        this.id = id;
        this.velocity = velocity;
        this.maxVelocity = 5;
        this.currentCell = currentCell;
        currentCell.setVehicle(this);

        this.fuelType = FuelType.Gasoline;
    }*/

    //////////// methods ////////////


    //////////// getter
    //////////// setter

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
//            manageSpeed(distanceToNextVehicle());

            accelerate();
            brake(distanceToNextVehicle());

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private double getConsumptionPerCell() {
        double consumption = Fuel.consumption.get(this.velocity);
        double cellSize = this.currentCell.getSize();
        double co2PerL = Fuel.co2PerL.get(this.fuelType);

        // consumption [l/100km]
        return (consumption / 100000) * cellSize * (co2PerL / 1);
    }

    /**
     *
     */
    /*private void manageSpeed(int distToNextVehicle) {
        if (this.velocity < this.maxVelocity &&
                this.velocity + 1 <= distToNextVehicle) {
            this.velocity++;
        }
    }*/
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

                    System.out.println("next Edge: " + nextEdge.getName());
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
        if (!Timer.getExpiredAgents().contains(this)) {
            System.out.println("expire: " + this.id);
            this.currentCell.setVehicle(null);

            Timer.getExpiredAgents().add(this);
        }
        this.expired = true;
    }


    // TODO repair
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
        // this.currentCell.increaseCo2Emission(this.getConsumptionPerCell());
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

    public int getCurrentVelocity() {
        return velocity;
    }



    /*    private int nextCell(Cell cell) {
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
    }*/
}