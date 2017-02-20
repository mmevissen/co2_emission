package model.environment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * connection between two {@Link Node}s. represents a street respectively a straight section of a street. <br/>
 * contains a number of streets - currently only 2 supported (entry and exit lane)
 */
public class Edge implements Serializable {

    private String name;

    private Node startNode;
    private Node endNode;

    private List<Lane> lanes = new ArrayList<>();

    // constructor
    public Edge(String name, Node startNode, Node endNode, int numOfLanes, int numberOfCells) {
        this.name = name;
        this.startNode = startNode;
        this.endNode = endNode;
        this.startNode.addEdge(this);
        this.endNode.addEdge(this);
        createLanes(numOfLanes, numberOfCells);
    }

    // getter
    public String getName() {
        return name;
    }

    public Node getStartNode() {
        return startNode;
    }

    public Node getEndNode() {
        return endNode;
    }

    public List<Lane> getLanes() {
        return lanes;
    }

    // methods
    private void createLanes(int numOfLanes, int numberOfCells) {

        for (int i = 1; i <= numOfLanes; i++) {
            lanes.add(new Lane(numberOfCells, (i % 2) == 0, this));
        }
    }

    @Override
    public String toString() {
        String s = "";
        for (Lane lane : lanes) {
            s += lane.toString() + "\n";
        }
        return s;
    }
}