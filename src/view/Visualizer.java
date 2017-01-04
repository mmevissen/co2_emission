package view;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Visualizer extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		int[] array = { 10, 10, 10, 10, 10 };

		primaryStage.setTitle("Hello World!");
		Button btn = new Button();
		btn.setText("Say 'Hello World'");
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("Hello World!");
			}
		});

		FlowPane root = new FlowPane();
		root.getChildren().add(btn);

		root.getChildren().addAll(visualizeLane(array));
		root.getChildren().addAll(visualizeLane(array));
		root.getChildren().addAll(visualizeLane(array));

		primaryStage.setScene(new Scene(root, 300, 250));
		primaryStage.show();
	}

	public HBox visualizeLane(int[] values) {		
		List<Rectangle> rectangles = new ArrayList<Rectangle>();
		
		for (int value : values) {
			Rectangle rec = new Rectangle(value, value, Color.BLUE);
			rectangles.add(rec);
		}
		
		HBox hb = new HBox();
		hb.getChildren().addAll(rectangles);
		
		return hb;
	}

}