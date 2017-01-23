/*
package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Visualizer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        int[] array = {10, 10, 10, 10, 10};

        FlowPane root = new FlowPane();

        root.getChildren().addAll(visualizeLane(array));

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public HBox visualizeLane(int[] values) {
        // List<Rectangle> rectangles = new ArrayList<Rectangle>();
        HBox hb = new HBox();
        hb.setSpacing(1);

        for (int value : values) {
            FlowPane fp = new FlowPane();
            Rectangle rec = new Rectangle(value, value, Color.BLUE);

            VBox cell = CellVisualisation(10);
            hb.getChildren().add(cell);

            rec.setHeight(20);


            hb.getChildren().add(rec);

            // hb.getChildren().add(fp);
        }

        return hb;
    }

    public VBox CellVisualisation(*/
/*Cell cell*//*
 double value) {
        VBox vb = new VBox();

        Font font = new Font(12);

        Label lb1 = new Label(String.format("occupied: %s", value*/
/* cell.getVehicle()!= null*//*
));
        Label lb2 = new Label(String.format("co2 emission: %s", value*/
/* cell.getCo2Emission()*//*
));

        lb1.setFont(font);
        lb2.setFont(font);

        vb.getChildren().add(lb1);
        vb.getChildren().add(lb2);

        return vb;
    }

}*/
