package model;

import model.agents.FuelType;
import model.agents.Vehicle;
import model.environment.Cell;
import model.environment.Edge;
import model.simulation.SimulationParameters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * generates new vehicles depending on a given probability at the known first entry lane cells
 */
public class VehicleBuilder {

    // variables
    private SimulationParameters parameters;
    private int i = 0;

    private List<Cell> entryCells;

    private int maxNumberOfVehicles = 0;

    /**
     * 0.0 - 1.0; sum = 1
     */
    private double gasolineProbability;
    private double dieselProbability;
    private double lpgProbability;
    // private double cngProbability;

    // statistical variables
    private int numberOfGeneratedVehicles = 0;
    private int numberOfGasoline = 0;
    private int numberOfDiesel = 0;
    private int numberOfLPG = 0;
    private int numberOfCNG = 0;

    // constructor
    public VehicleBuilder(List<Edge> edges, int maxNumberOfVehicles, SimulationParameters parameters) {
        super();
        this.entryCells = getEntryCells(edges);
        this.maxNumberOfVehicles = maxNumberOfVehicles;
        this.parameters = parameters;

        this.gasolineProbability = parameters.getProbabilityVehicleCreationGasoline();
        this.dieselProbability = this.gasolineProbability + parameters.getProbabilityVehicleCreationDiesel();
        this.lpgProbability = this.dieselProbability + parameters.getProbabilityVehicleCreationLPG();
        // cng = lpgProbability + parameters.getProbabilityVehicleCreationCNG();
    }

    // getter
    private List<Cell> getEntryCells(List<Edge> edges) {
        List<Cell> cells = new ArrayList<>();
        for (Edge edge : edges) {
            cells.add(edge.getLanes().get(0).getHead());
        }
        return cells;
    }

    public int getNumberOfGeneratedVehicles() {
        return numberOfGeneratedVehicles;
    }

    public HashMap<FuelType, Integer> getNumberOfGeneratedVehiclesByFuelType() {
        return new HashMap<FuelType, Integer>() {{
            put(FuelType.Gasoline, numberOfGasoline);
            put(FuelType.Diesel, numberOfDiesel);
            put(FuelType.LPG, numberOfLPG);
            put(FuelType.CNG, numberOfCNG);
        }};
    }

    public int getMaxNumberOfVehicles() {
        return this.maxNumberOfVehicles;
    }

    // methods
    /**
     * generates new vehicles with a certain configurable probability at the entry lanes (its first cells). <br/>
     * @return
     */
    public List<Vehicle> generateVehicle() {
        List<Vehicle> vehicles = new ArrayList<>();
        for (Cell cell : this.entryCells) {
            if (cell.getVehicle() == null && Math.random() <= this.parameters.getProbabilityVehicleCreationGeneral()) {
                if (this.maxNumberOfVehicles < 0) {
                    addVehicle(vehicles, cell);
                } else {
                    if (this.numberOfGeneratedVehicles < this.maxNumberOfVehicles) {
                        addVehicle(vehicles, cell);
                    }
                }
            }
        }
        return vehicles;
    }

    private void addVehicle(List<Vehicle> vehicles, Cell cell) {
        vehicles.add(new Vehicle(i++, cell, getFuelType()));
        numberOfGeneratedVehicles++;
    }

    /**
     * returns a {@Link FuelType} depending on its configured probability
     * @return
     */
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
}