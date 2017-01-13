package view;

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

    private Cell cell;
    private double maxCo2Value;

    private VBox cellVBox;
    private Label co2Label;
    private Rectangle carRect;


    public CellVisualisation(Cell cell, double maxCo2Value) {
        this.cell = cell;
        initialize();
        update(cell);
        this.maxCo2Value = maxCo2Value;
    }

    private void initialize() {
        this.cellVBox = new VBox();
        this.cellVBox.alignmentProperty().setValue(Pos.CENTER);
        this.co2Label = new Label("-,--");

        this.carRect = new Rectangle(10, 10);
        this.carRect.setFill(Color.CORAL);
        this.carRect.setStroke(Color.BEIGE);

        this.cellVBox.getChildren().add(co2Label);
        this.cellVBox.getChildren().add(carRect);
    }

    public VBox getCellVBox() {
        return cellVBox;
    }

    public void update(Cell cell) {

        if (cell.getVehicle() != null) {
            carRect.visibleProperty().setValue(Boolean.TRUE);
        } else {
            carRect.visibleProperty().setValue(Boolean.FALSE);
        }

        co2Label.setText(
                String.valueOf(
                        new DecimalFormat("#0.000").format(
                                (cell.getCo2Emission()))));

        Color col = Color.GREEN.interpolate(Color.RED, cell.getCo2Emission() /  this.maxCo2Value );
        cellVBox.backgroundProperty().setValue(new Background(new BackgroundFill(col, CornerRadii.EMPTY, Insets.EMPTY)));
    }
}
