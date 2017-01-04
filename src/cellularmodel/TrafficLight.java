package cellularmodel;

import java.util.List;

import agents.Agent;
import simulation.Timer;

public class TrafficLight extends Node implements TrafficRegulation, Agent {

	// private Node juctionNode;

	private long interval;

	private Edge goEdge;

	public Edge getGoEdge() {
		return goEdge;
	}

	private int edgeIndex = -1;

	public TrafficLight(long interval) {
		super();
		// this.juctionNode = juctionNode;
		this.interval = interval;
		// this.goEdge = this.getEdges().get(0);
	}

	// public List<Edge> getDirections(){
	// return this.juctionNode.getEdges();
	// }

	@Override
	public boolean update() {
		 System.out.println("TrafficLight index: " + this.goEdge.getName());
		return false;
	}

	@Override
	public boolean calculate() {
		if (Timer.getCurrentTime() % this.interval == 0) {
			edgeIndex++;
			if (edgeIndex >= this.getEdges().size()) {
				edgeIndex = 0;

			}
			this.goEdge = this.getEdges().get(edgeIndex);
		}

		return false;
	}
}
