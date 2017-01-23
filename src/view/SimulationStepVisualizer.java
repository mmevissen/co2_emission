package view;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.SimulationStep;
import model.agents.FuelType;
import model.environment.Cell;
import model.environment.Edge;
import model.environment.Lane;

import java.util.*;

public class SimulationStepVisualizer {

    private List<SimulationStep> simulationSteps;
    private VBox visualisation;
    private List<CellVisualisation> cellVisualisations;

    private double maxCo2Value;

    public SimulationStepVisualizer(List<SimulationStep> simulationSteps) {
        this.cellVisualisations = new ArrayList<>();
        this.simulationSteps = simulationSteps;
        this.visualisation = visualizeEnvironment(simulationSteps.get(0).getEdges());
    }

    public VBox getVisualisation() {
        return this.visualisation;
    }

    public void setTimeStep(int timeStep) {
        updateSimulation(this.simulationSteps.get(timeStep));
    }

    public HashMap<FuelType, Integer> getStepInfo(int timeStep) {
        return this.simulationSteps.get(timeStep).getNumbersOfVehicles();
    }

    public HashMap<FuelType, Integer> getGeneralInfo() {
        HashMap<FuelType, Integer> numberOfVehicles = new HashMap<>();

        for (SimulationStep step : this.simulationSteps) {
            Iterator iterator = step.getNumbersOfVehicles().entrySet().iterator();

            Map.Entry fuelType;
            while (iterator.hasNext()) {
                fuelType = (Map.Entry) iterator.next();

                if (numberOfVehicles.containsKey(fuelType.getKey())) {
                    int currentNumber = numberOfVehicles.get(fuelType.getKey());
                    numberOfVehicles.replace((FuelType) fuelType.getKey(), currentNumber + (int) fuelType.getValue());
                } else {
                    numberOfVehicles.put((FuelType) fuelType.getKey(), (int) fuelType.getValue());
                }
            }
        }

        return numberOfVehicles;
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
            this.cellVisualisations.add(cellVis);
        }
        resultHBox.setPadding(new Insets(0, 0, 1, 0));
        resultHBox.setSpacing(1);
        return resultHBox;
    }

    public int getNumberOfVehicles(int time) {
        return simulationSteps.get(time).getCurrentNumberOfVehicles();
    }

}
