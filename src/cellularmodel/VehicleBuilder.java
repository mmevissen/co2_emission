package cellularmodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import agents.FuelType;
import agents.Vehicle;
import simulation.SimulationParameters;

public class VehicleBuilder {

    /**
     * 0.0 - 1.0
     */
    private int i = 0;
    private List<Cell> cells;

    public int getMaxNumberOfVehicles() {
        return maxNumberOfVehicles;
    }

    private int maxNumberOfVehicles = 0;
    private int numberOfGeneratedVehicles = 0;

    private int numberOfGasoline = 0;
    private int numberOfDiesel = 0;
    private int numberOfLPG = 0;
    private int numberOfCNG = 0;

    private SimulationParameters parameters;

    private double gasolineProbability;
    private double dieselProbability;
    private double lpgProbability;

    public VehicleBuilder(List<Edge> edges, int maxNumberOfVehicles, SimulationParameters parameters) {
        super();
        this.cells = getStartCells(edges);
        this.maxNumberOfVehicles = maxNumberOfVehicles;
        this.parameters = parameters;

        this.gasolineProbability = parameters.getProbabilityVehicleCreationGasoline();
        this.dieselProbability = this.gasolineProbability + parameters.getProbabilityVehicleCreationDiesel();
        this.lpgProbability = this.dieselProbability + parameters.getProbabilityVehicleCreationLPG();
        // cng = lpgProbability + parameters.getProbabilityVehicleCreationCNG();
    }

    public List<Vehicle> generateVehicle() {
        List<Vehicle> vehicles = new ArrayList<>();
        for (Cell cell : cells) {
            if (cell.getVehicle() == null) {
                if (Math.random() <= parameters.getProbabilityVehicleCreationGeneral()) {
                    if (maxNumberOfVehicles < 0) {
                        GenerateVehicle(vehicles, cell);
                    } else {
                        if (numberOfGeneratedVehicles < maxNumberOfVehicles) {
                            GenerateVehicle(vehicles, cell);
                        }
                    }
                }
            }
        }
        return vehicles;
    }

    private void GenerateVehicle(List<Vehicle> vehicles, Cell cell) {
        vehicles.add(new Vehicle(i++, cell, getFuelType()));
        numberOfGeneratedVehicles++;
    }

    private FuelType getFuelType() {
        double probability = Math.random();

        if (probability <= this.gasolineProbability) {
            this.numberOfGasoline++;
            return FuelType.Gasoline;
        } else if (probability <= this.dieselProbability) {
            this.numberOfDiesel++;
            return FuelType.Diesel;
        } else if (probability <= this.lpgProbability) {
            this.numberOfLPG++;
            return FuelType.LPG;
        } else {
            this.numberOfCNG++;
            return FuelType.CNG;
        }
    }

    private List<Cell> getStartCells(List<Edge> edges) {
        List<Cell> cells = new ArrayList<Cell>();
        for (Edge edge : edges) {
            cells.add(edge.getLanes().get(0).getHead());
        }
        return cells;
    }

    public int getNumberOfGeneratedVehicles() {
        return numberOfGeneratedVehicles;
    }

    public HashMap<FuelType, Integer> getNumberOfGeneratedVehiclesByFuelType(){
        return new HashMap<FuelType, Integer>(){{
            put(FuelType.Gasoline, numberOfGasoline);
            put(FuelType.Diesel, numberOfDiesel);
            put(FuelType.LPG, numberOfLPG);
            put(FuelType.CNG, numberOfCNG);
        }};
    }
}
