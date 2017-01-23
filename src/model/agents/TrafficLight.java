package model.agents;

import model.environment.Edge;
import model.environment.Node;

public class TrafficLight extends Node implements Agent {

    private long interval;

    private Edge goEdge;

    private int edgeIndex = -1;

    public TrafficLight(long interval) {
        super();
        // this.juctionNode = juctionNode;
        this.interval = interval;
        // this.goEdge = this.getEdges().get(0);
    }

    public Edge getGoEdge() {
        return goEdge;
    }

    @Override
    public boolean update() {
        // System.out.println("TrafficLight index: " + this.goEdge.getName());
        return false;
    }

    @Override
    public boolean calculate(long currentTime) {
        if (currentTime % this.interval == 0) {
            edgeIndex++;
            if (edgeIndex >= this.getEdges().size()) {
                edgeIndex = 0;
            }
            this.goEdge = this.getEdges().get(edgeIndex);
            return true;
        }
        return false;
    }
}
