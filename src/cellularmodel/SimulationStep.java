package cellularmodel;

import java.util.ArrayList;
import java.util.List;

public class SimulationStep {

    private long currentSimulationTime;
    private int currentNumberOfVehicles;
    private int totalNumberOfVehicles;



    private ArrayList<Edge> edges = new ArrayList<>();


    public SimulationStep(long currentSimulationTime, int currentNumberOfVehicles,int totalNumberOfVehicles, ArrayList<Edge> edges) {
        this.currentSimulationTime = currentSimulationTime;
        this.currentNumberOfVehicles = currentNumberOfVehicles;
        this.totalNumberOfVehicles = totalNumberOfVehicles;
        this.edges = edges;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }



}
