package simulation;

import java.util.ArrayList;
import java.util.List;

import agents.Agent;
import agents.Vehicle;
import cellularmodel.Edge;
import cellularmodel.Node;
import cellularmodel.TrafficLight;

public class CaseOne {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int numberOfCells = 50;
		
		Node node1 = new Node();
		Node node2 = new Node();
		Node node3 = new Node();
		Node node4 = new Node();
		// junction node
		Node node5 = new TrafficLight(5);
		//		TrafficLight trafficLight =  new TrafficLight();
			
		List<Edge> edges = new ArrayList<Edge>();
		edges.add(new Edge("edgeSN", node1, node5, 2, numberOfCells));
		edges.add(new Edge("edgeWE", node2, node5, 2, numberOfCells));
		edges.add(new Edge("edgeNS", node3, node5, 2, numberOfCells));
		edges.add(new Edge("edgeEW", node4, node5, 2, numberOfCells));
		
		Timer.setSimulationTime(500);
		Timer.edges= edges;
		

				
//		Vehicle vehicle1 = new Vehicle(1, edges.get(0).getLanes().get(0).getCells().get(5));
//		Vehicle vehicle2 = new Vehicle(2, edges.get(0).getLanes().get(0).getHead(), 2);

		Timer.agents.add((Agent) node5);
//		Timer.agents.add(vehicle1);
//		Timer.agents.add(vehicle2);

		System.out.println(node1.toString());
		System.out.println(node2.toString());
		System.out.println(node3.toString());
		System.out.println(node4.toString());
		System.out.println(node5.toString());
		
		System.out.println("Initial state:");
		Timer.printEdges();
		Timer.startSimulation();	
		}
}
