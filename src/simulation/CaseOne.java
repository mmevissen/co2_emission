package simulation;

import java.io.IOException;
import simulation.environment.BasicEnvironment;

public class CaseOne {

    public static void main(String[] args) throws IOException {
        Timer.setSimulationTime(10800);
        Timer.edges = BasicEnvironment.getEnvironment(134, 30,2 );
        Timer.agents.addAll(BasicEnvironment.getEnvironmentAgents());
        Timer.startSimulation();
    }
}
