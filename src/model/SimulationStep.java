package model;

import model.agents.FuelType;
import model.environment.Cell;
import model.environment.Edge;
import model.environment.Lane;

import java.util.ArrayList;
import java.util.HashMap;

public class SimulationStep {

    private long currentSimulationTime;

    private int totalNumberOfVehicles;
    private int currentNumberOfVehicles;

    private HashMap<FuelType, Integer> numberOfVehicles = new HashMap<FuelType, Integer>();

    private double co2Value;


    private ArrayList<Edge> edges = new ArrayList<>();


    public SimulationStep(long currentSimulationTime, int currentNumberOfVehicles, int totalNumberOfVehicles, ArrayList<Edge> edges) {
        this.currentSimulationTime = currentSimulationTime;
        this.currentNumberOfVehicles = currentNumberOfVehicles;
        this.totalNumberOfVehicles = totalNumberOfVehicles;
        this.edges = edges;

        this.currentNumberOfVehicles = 0;

        this.co2Value = 0;

        for (Edge edge : edges) {
            for (Lane lane : edge.getLanes()) {
                for (Cell cell : lane.getCells()) {
                    if (this.co2Value < cell.getCo2Emission()) {
                        this.co2Value = cell.getCo2Emission();
                    }
                    if (cell.getVehicle() != null) {
                        currentNumberOfVehicles++;

                        FuelType fuelType = cell.getVehicle().getFuelType();
                        if (this.numberOfVehicles.containsKey(fuelType)) {
                            numberOfVehicles.replace(fuelType, numberOfVehicles.get(fuelType) + 1);
                        } else {
                            numberOfVehicles.put(fuelType, 1);
                        }

                    }
                }
            }
        }
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public double getCurrentCo2Value() {
        return co2Value;
    }

    public long getCurrentSimulationTime() {
        return currentSimulationTime;
    }

    public int getTotalNumberOfVehicles() {
        return totalNumberOfVehicles;
    }

    public int getCurrentNumberOfVehicles() {
        return currentNumberOfVehicles;
    }

    public HashMap<FuelType, Integer> getNumbersOfVehicles() {
        return numberOfVehicles;
    }
}
