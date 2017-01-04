package cellularmodel;

import java.util.ArrayList;
import java.util.List;

public class Node {

	private List<Edge> edges =  new ArrayList<Edge>();

	public List<Edge> getEdges() {
		return edges;
	}

	public void addEdge(Edge edge) {
		this.edges.add(edge);
	}

	@Override
	public String toString() {
		String s = "";
		for (Edge edge : edges) {
			s += edge.getName() + ", ";
		}
		return s;
	}

}
