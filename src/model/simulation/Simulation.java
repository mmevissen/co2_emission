package model.simulation;

import model.SimulationStep;
import model.VehicleBuilder;
import model.agents.Agent;
import model.agents.FuelType;
import model.agents.Vehicle;
import model.environment.Edge;
import tools.ObjectCloner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class Simulation {
    private static long currentTime;
    private static long endTime;
    private static SimulationParameters simulationParameters = null;
    private static VehicleBuilder builder;

    static List<Edge> edges = new ArrayList<>();

    static List<Agent> agents = new ArrayList<>();

    static List<Agent> expiredAgents = new ArrayList<>();

    public static void clear() {
        currentTime = 0;
        endTime = 0;
        edges.clear();
        agents.clear();
        expiredAgents.clear();
    }

    public static void calculate() {
        for (Agent agent : agents) {
            agent.calculate(currentTime);
        }
    }

    public static void update() {
        for (Agent agent : agents) {
            agent.update();
        }
    }

    public static List<SimulationStep> startSimulation() throws IOException {
        List<SimulationStep> simulationSteps = new ArrayList<>();
        builder = new VehicleBuilder(getEdges(), -1, simulationParameters);

        for (int i = 0; i < endTime; i++) {
            //  System.out.println("currentTime: " + i);

            calculate();
            update();
            agents.addAll(builder.generateVehicle());
            // printEdges();

            agents.removeAll(expiredAgents);
            expiredAgents.clear();

            simulationSteps.add(new SimulationStep(
                    currentTime,
                    getCurrentNumberOfVehicles(),
                    builder.getNumberOfGeneratedVehicles(),
                    (ArrayList) ObjectCloner.deepClone(edges)));

            currentTime++;
        }

        return simulationSteps;
    }

    static void printEdges() {
        for (Edge edge : edges) {
            System.out.println(edge.getName() + ":\n" + edge.toString());
        }
        System.out.println("----------------------");
    }


    public static void setSimulationTime(int i) {
        endTime = i;
    }

    private static int getCurrentNumberOfVehicles() {
        int result = 0;
        for (Agent agent : agents) {
            if (agent.getClass() == Vehicle.class) {
                result++;
            }
        }
        return result;
    }

    public static void setEnvironment(ArrayList<Edge> environment) {
        edges = environment;
    }

    public static void setAgents(List<Agent> agents) {
        Simulation.agents = agents;
    }

    public static void setSimulationParameters(SimulationParameters parameters) {
        simulationParameters = parameters;
    }

    public static long getCurrentTime() {
        return currentTime;
    }

    public static long getEndTime() {
        return endTime;
    }

    public static List<Edge> getEdges() {
        return edges;
    }

    public static List<Agent> getAgents() {
        return agents;
    }

    public static List<Agent> getExpiredAgents() {
        return expiredAgents;
    }

    public static HashMap<FuelType, Integer> getGeneratedVehicleNumbers() {
        return builder.getNumberOfGeneratedVehiclesByFuelType();
    }


}