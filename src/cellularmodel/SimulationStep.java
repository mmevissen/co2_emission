package cellularmodel;

import java.util.ArrayList;
import java.util.List;

public class SimulationStep {

    private long currentSimulationTime;
    private int currentNumberOfVehicles;
    private int totalNumberOfVehicles;
    private double maxCo2Value;


    private ArrayList<Edge> edges = new ArrayList<>();


    public SimulationStep(long currentSimulationTime, int currentNumberOfVehicles,int totalNumberOfVehicles, ArrayList<Edge> edges) {
        this.currentSimulationTime = currentSimulationTime;
        this.currentNumberOfVehicles = currentNumberOfVehicles;
        this.totalNumberOfVehicles = totalNumberOfVehicles;
        this.edges = edges;


        this.maxCo2Value = 0;

        for (Edge edge : edges) {
            for (Lane lane : edge.getLanes()) {
                for (Cell cell : lane.getCells()) {
                    if (this.maxCo2Value < cell.getCo2Emission())
                        this.maxCo2Value = cell.getCo2Emission();
                }
            }
        }
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public double getCurrentCo2Value() {
        return maxCo2Value;
    }
}
