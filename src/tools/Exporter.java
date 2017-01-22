package tools;

import cellularmodel.Cell;
import cellularmodel.Edge;
import cellularmodel.SimulationStep;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

/**
 * Created by Michael Mevissen on 15.01.2017.
 */

public final class Exporter {
    public static boolean writeCSV(List<SimulationStep> simulationStepList, Path outputFolderPath) {
        FileOutputStream os;

        int i = 0;
        File entryLaneFile;
        File exitLaneFile;

        StringBuilder entryStringBuilder;
        StringBuilder exitStringBuilder;


        for (SimulationStep step : simulationStepList) {

            entryStringBuilder = new StringBuilder();
            exitStringBuilder = new StringBuilder();

            for (Edge edge : step.getEdges()) {
                for (Cell cell : edge.getLanes().get(0).getCells()) {
                    entryStringBuilder.append(cell.getCo2Emission() + ";");
                }
                for (Cell cell : edge.getLanes().get(1).getCells()) {
                    exitStringBuilder.append(cell.getCo2Emission() + ";");
                }

                entryStringBuilder.append(System.lineSeparator());
                exitStringBuilder.append(System.lineSeparator());
            }

            entryLaneFile = Paths.get(outputFolderPath.toString(), "entry").toFile();
            exitLaneFile = Paths.get(outputFolderPath.toString(), "exit").toFile();

            try {
                entryLaneFile.mkdirs();
                exitLaneFile.mkdirs();

                entryLaneFile = Paths.get(entryLaneFile.toString(), i + ".csv").toFile();
                exitLaneFile = Paths.get(exitLaneFile.toString(), i + ".csv").toFile();

                os = new FileOutputStream(entryLaneFile);
                os.write(entryStringBuilder.toString().getBytes());
                os.close();

                os = new FileOutputStream(exitLaneFile);
                os.write(exitStringBuilder.toString().getBytes());
                os.close();


            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

            i++;
        }
        return true;
    }
}
