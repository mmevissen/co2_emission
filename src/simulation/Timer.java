package simulation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import agents.Agent;
import agents.Vehicle;
import cellularmodel.Edge;
import cellularmodel.SimulationStep;
import cellularmodel.VehicleBuilder;
import tools.ObjectCloner;

public final class Timer {
    private static long currentTime;
    private static long endTime;

    public static ArrayList<Edge> getEdges() {
        return edges;
    }

    public static List<Agent> getAgents() {
        return agents;
    }

    public static List<Agent> getExpiredAgents() {
        return expiredAgents;
    }

    public static void setAgents(List<Agent> agents) {
        Timer.agents = agents;
    }

    public static long getCurrentTime() {
        return currentTime;
    }

    public static long getEndTime() {
        return endTime;
    }


    static ArrayList<Edge> edges = new ArrayList<Edge>();

    static List<Agent> agents = new ArrayList<Agent>();

    static List<Agent> expiredAgents = new ArrayList<Agent>();

    public static void calculate() {
        for (Agent agent : agents) {
            agent.calculate();
        }
    }

    public static void update() {
        for (Agent agent : agents) {
            agent.update();
        }
    }

    public static List<SimulationStep> startSimulation() throws IOException {
        List<SimulationStep> simulationSteps = new ArrayList<>();
        VehicleBuilder builder = new VehicleBuilder(.2, getEdges(), -1);

        for (int i = 0; i < endTime; i++) {
            System.out.println("currentTime: " + i);

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
}