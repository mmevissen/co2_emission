package view;

import agents.Vehicle;
import cellularmodel.Cell;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.text.DecimalFormat;


public class CellVisualisation {
    public static double currentMaxCo2Value;

    private Cell cell;


    private VBox cellVBox;
    private Label co2Label;
    private Rectangle carRect;


    public CellVisualisation(Cell cell, double maxCo2Value) {
        this.cell = cell;
        initialize();
        update(cell);
    }

    private void initialize() {
        this.cellVBox = new VBox();
        this.cellVBox.alignmentProperty().setValue(Pos.CENTER);
        this.co2Label = new Label("-,--");

        this.carRect = new Rectangle(10, 10);
        this.carRect.setStroke(Color.BEIGE);

        this.cellVBox.getChildren().add(co2Label);
        this.cellVBox.getChildren().add(carRect);
    }

    public VBox getCellVBox() {
        return cellVBox;
    }

    public void update(Cell cell) {
        Vehicle vehicle = cell.getVehicle();
        if (vehicle != null) {
            switch (vehicle.getFuelType()) {
                case Gasoline:
                    carRect.setFill(Color.LIGHTBLUE);
                    break;
                case Diesel:
                    carRect.setFill(Color.BLACK);
                    break;
                case LPG:
                    carRect.setFill(Color.WHITE);
                    break;
                case CNG:
                    carRect.setFill(Color.CORAL);
                    break;
                default:
                    carRect.setFill(Color.PINK);
                    break;
            }

            carRect.visibleProperty().setValue(Boolean.TRUE);
        } else {
            carRect.visibleProperty().setValue(Boolean.FALSE);
        }

        co2Label.setText(
                String.valueOf(
                        new DecimalFormat("#0.000").format(
                                (cell.getCo2Emission()))));

        Color col = getColor(cell.getCo2Emission());
        cellVBox.backgroundProperty().setValue(new Background(new BackgroundFill(col, CornerRadii.EMPTY, Insets.EMPTY)));
    }


    private Color getColor(double value) {

        double mid = this.currentMaxCo2Value / 2;

        if (value < mid) {
            return Color.GREEN.interpolate(Color.YELLOW, value / mid);
        }
        return Color.YELLOW.interpolate(Color.RED, value / this.currentMaxCo2Value);
    }
}
