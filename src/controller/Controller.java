package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.SimulationStep;
import model.agents.FuelType;
import model.simulation.SimulationParameters;
import model.simulation.Simulator;
import view.SimulationStepVisualizer;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;


public class Controller {

    ///////// controls ///////
    @FXML
    private Button nextStepButton;
    @FXML
    private Button simulateButton;
    @FXML
    private Button chooseFolderButton;

    @FXML
    private Slider simulationStepSlider;

    @FXML
    private Pane simulationVisualizationPane;

    @FXML
    private TextField pathTextFiled;
    @FXML
    private TextField simulationRunsTextField;
    @FXML
    private TextField maxVehicleTextField;
    @FXML
    private TextField numberOfCellsTextField;
    @FXML
    private TextField simulationTimeTextField;
    @FXML
    private TextField trafficLightIntervalTextField;

    @FXML
    private TextField vehicleProbabilityTextField;
    @FXML
    private TextField gasolineProbabilityTextField;
    @FXML
    private TextField dieselProbabilityTextField;
    @FXML
    private TextField cngProbabilityTextField;
    @FXML
    private TextField lpgProbabilityTextField;


    @FXML
    private Label generalNumberOfVehiclesLabel;
    @FXML
    private Label generalNumberOfVehiclesGasolineLabel;
    @FXML
    private Label generalNumberOfVehiclesDieselLabel;
    @FXML
    private Label generalNumberOfVehiclesCNGLabel;
    @FXML
    private Label generalNumberOfVehiclesLPGLabel;
    @FXML
    private Label stepNumberOfVehiclesLabel;
    @FXML
    private Label stepNumberOfVehiclesGasolineLabel;
    @FXML
    private Label stepNumberOfVehiclesDieselLabel;
    @FXML
    private Label stepNumberOfVehiclesLPGLabel;
    @FXML
    private Label stepNumberOfVehiclesCNGLabel;

    private List<SimulationStep> results;
    private SimulationStepVisualizer simulationStepVisualizer;
    private Stage stage;
    private ChangeListener sliderChangeListener;

    private Path outPath = null;

    ///////// methods /////////

    public void initializeSimulationResultView(List<SimulationStep> results) {
        this.results = results;
        this.simulationStepVisualizer = new SimulationStepVisualizer(results);

        this.simulationStepSlider.setMax(results.size() - 1);
        this.simulationStepSlider.setValue(simulationStepSlider.getMin());

        if (this.sliderChangeListener == null)
            this.sliderChangeListener = getSliderChangedListener();

        simulationStepSlider.valueChangingProperty().addListener(getSliderChangedListener());


        this.simulationVisualizationPane.getChildren().add(simulationStepVisualizer.getVisualisation());
    }

    private void updateStepValues() {
        int time = (int) simulationStepSlider.getValue();

        this.stepNumberOfVehiclesLabel.setText(String.valueOf(simulationStepVisualizer.getNumberOfVehicles(time)));

        HashMap<FuelType, Integer> numbers = simulationStepVisualizer.getStepInfo((time));
        this.stepNumberOfVehiclesGasolineLabel.setText(String.valueOf(numbers.get(FuelType.Gasoline)));
        this.stepNumberOfVehiclesDieselLabel.setText(String.valueOf(numbers.get(FuelType.Diesel)));
        this.stepNumberOfVehiclesLPGLabel.setText(String.valueOf(numbers.get(FuelType.LPG)));
        this.stepNumberOfVehiclesCNGLabel.setText(String.valueOf(numbers.get(FuelType.CNG)));
    }

    private void updateGeneralNumbers(HashMap<FuelType, Integer> numbers) {
        int time = (int) simulationStepSlider.getValue();

        this.generalNumberOfVehiclesLabel.setText("");

        this.generalNumberOfVehiclesGasolineLabel.setText(String.valueOf(numbers.get(FuelType.Gasoline)));
        this.generalNumberOfVehiclesDieselLabel.setText(String.valueOf(numbers.get(FuelType.Diesel)));
        this.generalNumberOfVehiclesLPGLabel.setText(String.valueOf(numbers.get(FuelType.LPG)));
        this.generalNumberOfVehiclesCNGLabel.setText(String.valueOf(numbers.get(FuelType.CNG)));
    }


    private ChangeListener<Boolean> getSliderChangedListener() {
        return new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                simulationStepVisualizer.setTimeStep((int) simulationStepSlider.getValue());
                updateStepValues();
            }
        };
    }

    @FXML
    void onStepButtonPressed(ActionEvent event) {
        if (event.getSource().equals(nextStepButton)) {
            simulationStepSlider.increment();
            simulationStepVisualizer.setTimeStep((int) simulationStepSlider.getValue());
            updateStepValues();
        } else
            simulationStepSlider.decrement();
        simulationStepVisualizer.setTimeStep((int) simulationStepSlider.getValue());
        updateStepValues();
    }

    @FXML
    void simulationStepSliderChanged(MouseEvent event) {
        if (event.getSource().equals(simulationStepSlider)) {
            simulationStepVisualizer.setTimeStep((int) simulationStepSlider.getValue());
            updateStepValues();
        }
    }

    @FXML
    void clearPathButtonPressed(ActionEvent event) {
        this.outPath = null;
        this.pathTextFiled.setText("");
    }

    @FXML
    void simulateButtonPressed(ActionEvent event) {
        SimulationParameters parameters = getSimulationParameters();
        if (parameters == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error(s) in model.simulation parameters");
            alert.showAndWait();
        }

        System.out.println(parameters.toString());

        long startTime, endTime;
        Simulator simulator = new Simulator(parameters);

        try {
            startTime = System.currentTimeMillis();

            List<SimulationStep> results = simulator.startSimulation();

            /*new Thread(){

                @Override
                public void run() { }
            }.start();*/

            endTime = System.currentTimeMillis();

            initializeSimulationResultView(results);
            updateGeneralNumbers(simulator.getGeneralNumbers());

            System.out.println("Simulation Time: " + ((endTime - startTime) / 1000) + " s" + " runs: " + parameters.getSimulationRuns());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void chooseFolderButtonPressed(ActionEvent event) {
        try {
            DirectoryChooser chooser = new DirectoryChooser();
            chooser.setTitle("Choose Output Folder for the last model.simulation steps");
            File file = chooser.showDialog(this.stage.getScene().getWindow());

            this.outPath = file.toPath();
            pathTextFiled.setText(file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
            this.outPath = null;
        }
    }

    public void setSliderMaxValue(double i) {
        System.out.println("settings slider");
        this.simulationStepSlider.setMax(i);
        this.simulationStepSlider.setMajorTickUnit(i / 4);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    public SimulationParameters getSimulationParameters() {
        try {
            return new SimulationParameters(
                    Integer.parseInt(this.simulationRunsTextField.getText()),
                    Integer.parseInt(this.simulationTimeTextField.getText()),
                    Integer.parseInt(this.numberOfCellsTextField.getText()),
                    Integer.parseInt(this.maxVehicleTextField.getText()),
                    Integer.parseInt(this.trafficLightIntervalTextField.getText()),
                    Double.parseDouble(this.vehicleProbabilityTextField.getText()),
                    Double.parseDouble(this.gasolineProbabilityTextField.getText()),
                    Double.parseDouble(this.dieselProbabilityTextField.getText()),
                    Double.parseDouble(this.lpgProbabilityTextField.getText()),
                    Double.parseDouble(this.cngProbabilityTextField.getText()),
                    this.outPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}