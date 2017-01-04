package cellularmodel;

import java.util.ArrayList;
import java.util.List;

public class Edge {

	private String name;
	private Node startNode;
	private Node endNode;

	public String getName() {
		return name;
	}

	public Node getStartNode() {
		return startNode;
	}

	public Node getEndNode() {
		return endNode;
	}

	private List<Lane> lanes = new ArrayList<Lane>();

	public Edge(String name, Node startNode, Node endNode, int numOfLanes, int numberOfCells) {
		this.name = name;
		this.startNode = startNode;
		this.endNode = endNode;
		this.startNode.addEdge(this);
		this.endNode.addEdge(this);		
		createLanes(numOfLanes, numberOfCells);
	}

	public List<Lane> getLanes() {
		return lanes;
	}

	private void createLanes(int numOfLanes, int numberOfCells) {

		for (int i = 1; i <= numOfLanes; i++) {
			lanes.add(new Lane(numberOfCells, (i % 2) == 0, this));
			System.out.println("Line number: " + i);
		}
	}
	
	@Override
	public String toString() {
		String s = "";
		for (Lane lane : lanes) {
			s += lane.toString()+ "\n";
		}
		return s;
	}
}
