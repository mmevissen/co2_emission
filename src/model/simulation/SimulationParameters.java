package model.simulation;

import java.nio.file.Path;

public class SimulationParameters {

    private int simulationRuns;
    private int simulationTime;
    private int cellNumber;
    private int maxNumberOfVehicles;
    private int trafficLightInterval;

    private double probabilityVehicleCreationGeneral;
    private double probabilityVehicleCreationGasoline;
    private double probabilityVehicleCreationDiesel;
    private double probabilityVehicleCreationLPG;
    private double probabilityVehicleCreationCNG;

    private Path outputPath;

    public SimulationParameters(int simulationRuns, int simulationTime, int cellNumber, int maxNumberOfVehicles, int trafficLightInterval, double probabilityVehicleCreationGeneral, double probabilityVehicleCreationGasoline, double probabilityVehicleCreationDiesel, double probabilityVehicleCreationLPG, double probabilityVehicleCreationCNG, Path outputPath) {
        this.simulationRuns = simulationRuns;
        this.simulationTime = simulationTime;
        this.cellNumber = cellNumber;
        this.maxNumberOfVehicles = maxNumberOfVehicles;
        this.trafficLightInterval = trafficLightInterval;
        this.probabilityVehicleCreationGeneral = probabilityVehicleCreationGeneral;
        this.probabilityVehicleCreationGasoline = probabilityVehicleCreationGasoline;
        this.probabilityVehicleCreationDiesel = probabilityVehicleCreationDiesel;
        this.probabilityVehicleCreationLPG = probabilityVehicleCreationLPG;
        this.probabilityVehicleCreationCNG = probabilityVehicleCreationCNG;
        this.outputPath = outputPath;
    }

    public int getSimulationRuns() {
        return simulationRuns;
    }

    public int getSimulationTime() {
        return simulationTime;
    }

    public int getCellNumber() {
        return cellNumber;
    }

    public int getMaxNumberOfVehicles() {
        return maxNumberOfVehicles;
    }

    public int getTrafficLightInterval() {
        return trafficLightInterval;
    }

    public double getProbabilityVehicleCreationGeneral() {
        return probabilityVehicleCreationGeneral;
    }

    public double getProbabilityVehicleCreationGasoline() {
        return probabilityVehicleCreationGasoline;
    }

    public double getProbabilityVehicleCreationDiesel() {
        return probabilityVehicleCreationDiesel;
    }

    public double getProbabilityVehicleCreationLPG() {
        return probabilityVehicleCreationLPG;
    }

    public double getProbabilityVehicleCreationCNG() {
        return probabilityVehicleCreationCNG;
    }

    public Path getOutputPath() {
        return outputPath;
    }

    @Override
    public String toString() {
        return "simulationRuns; " + simulationRuns + ";" + System.lineSeparator() +
                "simulationTime; " + simulationTime + ";" + System.lineSeparator() +
                "cellNumber; " + cellNumber + ";" + System.lineSeparator() +
                "trafficLightInterval; " + trafficLightInterval + ";" + System.lineSeparator() +
                "probabilityVehicleCreationGeneral; " + probabilityVehicleCreationGeneral + ";" + System.lineSeparator() +
                "probabilityVehicleCreationGasoline; " + probabilityVehicleCreationGasoline + ";" + System.lineSeparator() +
                "probabilityVehicleCreationDiesel; " + probabilityVehicleCreationDiesel + ";" + System.lineSeparator() +
                "probabilityVehicleCreationLPG; " + probabilityVehicleCreationLPG + ";" + System.lineSeparator() +
                "probabilityVehicleCreationCNG; " + probabilityVehicleCreationCNG + ";" + System.lineSeparator() +
                "outputPath;" + outputPath + ";";
    }
}
