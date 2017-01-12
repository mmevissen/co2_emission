package simulation;

import java.io.IOException;
import simulation.environment.BasicEnvironment;

public class CaseOne {

    public static void main(String[] args) throws IOException {

        Timer.setSimulationTime(50);
        Timer.edges = BasicEnvironment.getEnvironment();
        Timer.agents.addAll(BasicEnvironment.getEnvironmentAgents());
        Timer.startSimulation();
    }
}
