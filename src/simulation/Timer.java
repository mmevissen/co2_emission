package simulation;

import java.util.ArrayList;
import java.util.List;

import agents.Agent;
import agents.Vehicle;
import cellularmodel.Cell;
import cellularmodel.Edge;
import cellularmodel.VehicleBuilder;

public final class Timer {

	private static long currentTime;
	private static long endTime;
	public static List<Edge> getEdges() {
		return edges;
	}

	public static void setEdges(List<Edge> edges) {
		Timer.edges = edges;
	}

	public static List<Agent> getAgents() {
		return agents;
	}
	public static List<Agent> getExpiredAgents() {
		return expiredAgents;
	}

	public static void setAgents(List<Agent> agents) {
		Timer.agents = agents;
	}

	public static long getCurrentTime() {
		return currentTime;
	}

	public static long getEndTime() {
		return endTime;
	}


	static List<Edge> edges = new ArrayList<Edge>();
	
	static List<Agent> agents = new ArrayList<Agent>();
	
	static List<Agent> expiredAgents = new ArrayList<Agent>();

	public static void calculate(){
		for(Agent agent : agents){
			agent.calculate();
		}
	}
	
	public static void update(){
		for(Agent agent : agents){
			agent.update();
		}
	}

	public static void startSimulation() {
		VehicleBuilder builder = new VehicleBuilder(.2, getEdges(), 1);
		
		for(int i = 0; i < endTime; i++){
			System.out.println("currentTime: " + i);
			
			calculate();
			update();
			agents.addAll(builder.generateVehicle());
			printEdges();
			
			agents.removeAll(expiredAgents);
			expiredAgents.clear();
			currentTime++;
		}
	}
	
	static void  printEdges(){
		for(Edge edge : edges){
			System.out.println(edge.getName() + ":\n" + edge.toString());
		}
		System.out.println("----------------------");
	}


	public static void setSimulationTime(int i) {
		endTime = i;		
	}
}
