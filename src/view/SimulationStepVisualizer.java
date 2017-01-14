package view;

import cellularmodel.Cell;
import cellularmodel.Edge;
import cellularmodel.Lane;
import cellularmodel.SimulationStep;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class SimulationStepVisualizer {

    private List<SimulationStep> simulationSteps;
    private VBox visualisation;
    private List<CellVisualisation> cellVisualisations = new ArrayList<>();

    private double maxCo2Value;

    SimulationStepVisualizer(List<SimulationStep> simulationSteps) {
        this.simulationSteps = simulationSteps;
        this.visualisation = visualizeEnvironment(simulationSteps.get(0).getEdges());
    }

    public VBox getVisualisation() {
        return this.visualisation;
    }

    public void setTimeStep(int timeStep) {
        updateSimulation(this.simulationSteps.get(timeStep));
    }

    private void updateSimulation(SimulationStep simulationStep) {
        int i = 0;
        //  if (sameLength()) {
        for (Edge edge : simulationStep.getEdges()) {
            for (Lane lane : edge.getLanes()) {
                for (Cell cell : lane.getCells()) {
                    CellVisualisation.currentMaxCo2Value = simulationStep.getCurrentCo2Value();
                    cellVisualisations.get(i).update(cell);
                    i++;
                }

            }
        }
        // }
    }

    private VBox visualizeEnvironment(ArrayList<Edge> environment) {
        VBox result = new VBox();

        for (Edge edge : environment) {
            result.getChildren().add(visualizeEdge(edge));
        }
        result.getChildren().add(new Rectangle(10, 2, Color.LIGHTGRAY));
        return result;
    }

    private VBox visualizeEdge(Edge egde) {
        VBox resultLaneVBox = new VBox();
        for (Lane lane : egde.getLanes()) {
            resultLaneVBox.getChildren().add(visualizeLane(lane));
        }
        resultLaneVBox.setPadding(new Insets(0, 0, 10, 0));
        return resultLaneVBox;
    }

    private HBox visualizeLane(Lane lane) {
        HBox resultHBox = new HBox();

        for (Cell cell : lane.getCells()) {
            CellVisualisation cellVis = new CellVisualisation(cell, this.maxCo2Value);
            resultHBox.getChildren().add(cellVis.getCellVBox());
            cellVisualisations.add(cellVis);
        }
        resultHBox.setPadding(new Insets(0, 0, 1, 0));
        resultHBox.setSpacing(1);
        return resultHBox;
    }
}
