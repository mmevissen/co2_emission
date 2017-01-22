package simulation.environment;

import agents.Agent;
import cellularmodel.Edge;
import cellularmodel.Node;
import cellularmodel.TrafficLight;

import java.util.ArrayList;

public class BasicEnvironment {

    private ArrayList<Edge> edges;
    private ArrayList<Agent> agents;

    public BasicEnvironment(int numberOfCells, int trafficLightInterval, int numberOfLanes) {
        agents = new ArrayList<>();
        initialize(numberOfCells, trafficLightInterval, numberOfLanes);
    }

    private void initialize(int numberOfCells, int trafficLightInterval, int numberOfLanes) {
        Node node1 = new Node();
        Node node2 = new Node();
        Node node3 = new Node();
        Node node4 = new Node();

        // junction node
        Node node5 = new TrafficLight(trafficLightInterval);
        this.agents.add((Agent) node5);

        this.edges = new ArrayList<>();
        this.edges.add(new Edge("edgeSN", node1, node5, numberOfLanes, numberOfCells));
        this.edges.add(new Edge("edgeWE", node2, node5, numberOfLanes, numberOfCells));
        this.edges.add(new Edge("edgeNS", node3, node5, numberOfLanes, numberOfCells));
        this.edges.add(new Edge("edgeEW", node4, node5, numberOfLanes, numberOfCells));
    }

    // getter
    public ArrayList<Agent> getEnvironmentAgents() {
        return agents;
    }

    public ArrayList<Edge> getEnvironment() {
        return edges;
    }
}
