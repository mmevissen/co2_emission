package tools;

import model.SimulationStep;
import model.agents.FuelType;
import model.environment.Cell;
import model.environment.Edge;
import model.simulation.SimulationParameters;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

    public static boolean writeStatisticsCSV(List<HashMap<FuelType, Integer>> statisticsResultsForToExport, SimulationParameters parameters, Path outputFolderPath) {
        FileOutputStream os;
        File statisticsFile;
        StringBuilder statisticsStringBuilder;

        int i = 0;

        // write model.simulation parameters
        try {
            statisticsStringBuilder = new StringBuilder(parameters.toString());
            statisticsFile = Paths.get(outputFolderPath.toString(), "parameters.csv").toFile();

            os = new FileOutputStream(statisticsFile);
            os.write(statisticsStringBuilder.toString().getBytes());
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        statisticsFile = Paths.get(outputFolderPath.toString(), "statistics").toFile();
        statisticsFile.mkdirs();

        for (HashMap<FuelType, Integer> statistics : statisticsResultsForToExport) {

            statisticsStringBuilder = new StringBuilder();

            Iterator iterator = statistics.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();

                statisticsStringBuilder.append(entry.getKey().toString() + ";" + entry.getValue() + ";");
                statisticsStringBuilder.append(System.lineSeparator());
            }

            try {
                os = new FileOutputStream(Paths.get(statisticsFile.toString(), i + ".csv").toFile());
                os.write(statisticsStringBuilder.toString().getBytes());
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
