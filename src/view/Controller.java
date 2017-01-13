package view;
import cellularmodel.SimulationStep;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.List;


public class Controller {

    ///////// controls ///////
    boolean increase = false;
    @FXML
    private Button pauseButton;
    @FXML
    private Button startButton;
    @FXML
    private Slider simulationStepSlider;

    @FXML
    private Button nextStepButton;

    @FXML
    private Pane simulationVisualizationPane;

    private List<SimulationStep> results;
    private SimulationStepVisualizer simulationStepVisualizer;

    ///////// methods /////////

    public void initializeSimulationView(List<SimulationStep> results) {
        this.simulationStepSlider.setMax(results.size() - 1);
        simulationStepSlider.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                simulationStepVisualizer.setTimeStep((int) simulationStepSlider.getValue());
            }
        });
        this.results = results;
        this.simulationStepVisualizer = new SimulationStepVisualizer(results);
        this.simulationVisualizationPane.getChildren().add(simulationStepVisualizer.getVisualisation());
    }

    @FXML
    public void startButtonClicked(ActionEvent event) throws InterruptedException {
        /*if(event.getSource().equals(startButton)) this.increase = true;
        else this.increase = false;

        while (increase) {
            simulationStepSlider.setValue(simulationStepSlider.getValue() + 1);

            try {
               Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
    }

    @FXML
    void onStepButtonPressed(ActionEvent event) {
        if (event.getSource().equals(nextStepButton)) {
            simulationStepSlider.increment();
            simulationStepVisualizer.setTimeStep((int) simulationStepSlider.getValue());
        } else
            simulationStepSlider.decrement();
        simulationStepVisualizer.setTimeStep((int) simulationStepSlider.getValue());
    }

    @FXML
    void simulationStepSliderChanged(MouseEvent event) {
        if(event.getSource().equals(simulationStepSlider)){
            simulationStepVisualizer.setTimeStep((int) simulationStepSlider.getValue());
        }
    }

    public void setSliderMaxValue(double i) {
        System.out.println("settings slider");
        this.simulationStepSlider.setMax(i);
        this.simulationStepSlider.setMajorTickUnit(i / 4);
    }
}