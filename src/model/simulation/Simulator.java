package model.simulation;

import model.SimulationStep;
import model.agents.FuelType;
import model.environment.BasicEnvironment;
import tools.Exporter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class Simulator {

    private SimulationParameters parameters;
    private BasicEnvironment environment;

    private HashMap<FuelType, Integer> generalNumbers;

    public Simulator(SimulationParameters parameters) {
        this.parameters = parameters;
    }

    public List<SimulationStep> startSimulation() throws IOException {

        List<SimulationStep> resultsForToExport = new ArrayList<>();
        List<HashMap<FuelType, Integer>> statisticsResultsForToExport = new ArrayList<>();

        List<SimulationStep> results = new ArrayList<>();


        for (int i = 0; i < parameters.getSimulationRuns(); i++) {
            long startTime = System.currentTimeMillis();
            System.out.println("run: " + i + "/" + parameters.getSimulationRuns() + " calculating ...");

            Simulation.clear();
            this.environment = new BasicEnvironment(parameters.getCellNumber(), parameters.getTrafficLightInterval(), 2);

            // Simulation model.simulation = new Simulation(parameters.getSimulationTime(), environment.getEnvironment(), environment.getEnvironmentAgents()); // Simulation noch statisch

            Simulation.setSimulationParameters(parameters);
            Simulation.setSimulationTime(parameters.getSimulationTime());
            Simulation.setEnvironment(environment.getEnvironment());
            Simulation.setAgents(environment.getEnvironmentAgents());


            results = Simulation.startSimulation();
            resultsForToExport.add(results.get(results.size() - 1));
            generalNumbers = Simulation.getGeneratedVehicleNumbers();
            statisticsResultsForToExport.add(generalNumbers);


            long endTime = System.currentTimeMillis();
            System.out.println("finished: " + (endTime - startTime) + " ms");
        }

        // save the model.simulation run results if a path was selected
        if (parameters.getOutputPath() != null) {
            Exporter.writeCSV(resultsForToExport, parameters.getOutputPath());
            Exporter.writeStatisticsCSV(statisticsResultsForToExport, parameters, parameters.getOutputPath());
        }


        return results;
    }

    public HashMap<FuelType, Integer> getGeneralNumbers() {
        return generalNumbers;
    }
}
