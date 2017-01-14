package view;

import cellularmodel.SimulationStep;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import simulation.Timer;
import simulation.environment.BasicEnvironment;

import java.io.IOException;
import java.util.List;

public class SimulationView extends Application {

    private String title = "co2 emission simulation";
    private long startTime;
    private long endTime;


    private static FXMLLoader fxmlLoader = new FXMLLoader();

    public static void main(String[] args) throws InterruptedException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException, InterruptedException {
        Parent root = fxmlLoader.load(getClass().getResource("StartView.fxml").openStream());
        Controller controller = fxmlLoader.getController();

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle(title + " - SIMULATING...");
        primaryStage.show();

        Timer.setSimulationTime(10800);
        Timer.setEnvironment(BasicEnvironment.getEnvironment(134, 30, 2));
        Timer.setAgents(BasicEnvironment.getEnvironmentAgents());
        this.startTime = System.currentTimeMillis();
        List<SimulationStep> results = Timer.startSimulation();
        this.endTime = System.currentTimeMillis();


        controller.initializeSimulationView(results);

        primaryStage.setTitle(title + " - " + ((endTime - startTime) / 1000) + " s");
        primaryStage.sizeToScene();

        // controller.setSliderMaxValue(500);
        // controller.setSlider(20);
    }
}
