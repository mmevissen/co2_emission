package simulation.environment;

import agents.Agent;
import cellularmodel.Edge;
import cellularmodel.Node;
import cellularmodel.TrafficLight;

import java.util.ArrayList;


public final class BasicEnvironment {

    private static ArrayList<Agent> agents = new ArrayList<>();


    public static ArrayList<Agent> getEnvironmentAgents() {
        return agents;
    }

    public static ArrayList<Edge> getEnvironment() {
        return getEnvironment(100, 5, 2);
    }

    public static ArrayList<Edge> getEnvironment(int numberOfCells, int trafficLightInterval, int numberOfLanes) {

        Node node1 = new Node();
        Node node2 = new Node();
        Node node3 = new Node();
        Node node4 = new Node();
        // junction node
        Node node5 = new TrafficLight(trafficLightInterval);
        agents.add((Agent) node5);

        ArrayList<Edge> edges = new ArrayList<>();
        edges.add(new Edge("edgeSN", node1, node5, numberOfLanes, numberOfCells));
        edges.add(new Edge("edgeWE", node2, node5, numberOfLanes, numberOfCells));
        edges.add(new Edge("edgeNS", node3, node5, numberOfLanes, numberOfCells));
        edges.add(new Edge("edgeEW", node4, node5, numberOfLanes, numberOfCells));

        return edges;
    }
}
