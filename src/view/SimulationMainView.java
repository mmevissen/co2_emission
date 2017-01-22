package view;

import cellularmodel.SimulationStep;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import simulation.Simulation;
import simulation.environment.BasicEnvironment;

import java.io.IOException;
import java.util.List;

public class SimulationMainView extends Application {

    private String title = "co2 emission simulation";
    private long startTime;
    private long endTime;


    private static FXMLLoader fxmlLoader = new FXMLLoader();

    public static void main(String[] args) throws InterruptedException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException, InterruptedException {
        Parent root = fxmlLoader.load(getClass().getResource("SimulationMainView.fxml").openStream());
        Scene scene = new Scene(root);

        Controller controller = fxmlLoader.getController();
        controller.setStage(primaryStage);

        primaryStage.setScene(scene);
        primaryStage.setTitle(title);
        primaryStage.show();

        /*Simulation.setSimulationTime(1000);
        Simulation.setEnvironment(BasicEnvironment.getEnvironment(134, 30, 2));
        Simulation.setAgents(BasicEnvironment.getEnvironmentAgents());
        this.startTime = System.currentTimeMillis();
        List<SimulationStep> results = Simulation.startSimulation();
        this.endTime = System.currentTimeMillis();

        controller.initializeSimulationResultView(results);

        primaryStage.setTitle(title + " - " + ((endTime - startTime) / 1000) + " s");*/

        primaryStage.sizeToScene();

    }
}
