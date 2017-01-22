package simulation;

import agents.FuelType;
import cellularmodel.SimulationStep;
import simulation.environment.BasicEnvironment;
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
        List<SimulationStep> results = new ArrayList<>();


        for (int i = 0; i < parameters.getSimulationRuns(); i++) {
            Simulation.clear();
            this.environment = new BasicEnvironment(parameters.getCellNumber(), parameters.getTrafficLightInterval(), 2);

            // Simulation simulation = new Simulation(parameters.getSimulationTime(), environment.getEnvironment(), environment.getEnvironmentAgents()); // Simulation noch statisch
            Simulation.setSimulationParameters(parameters);
            Simulation.setSimulationTime(parameters.getSimulationTime());
            Simulation.setEnvironment(environment.getEnvironment());
            Simulation.setAgents(environment.getEnvironmentAgents());


            results = Simulation.startSimulation();
            resultsForToExport.add(results.get(results.size() - 1));
            generalNumbers = Simulation.getGeneratedVehicleNumbers();
        }

        // save the simulation run results if a path was selected
        if (parameters.getOutputPath() != null)
            Exporter.writeCSV(resultsForToExport, parameters.getOutputPath());

        return results;
    }

    public HashMap<FuelType, Integer> getGeneralNumbers() {
        return generalNumbers;
    }
}
