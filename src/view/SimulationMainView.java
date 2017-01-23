package view;

import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SimulationMainView extends Application {

    private String title = "co2 emission model.simulation";
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

        primaryStage.sizeToScene();

    }
}
