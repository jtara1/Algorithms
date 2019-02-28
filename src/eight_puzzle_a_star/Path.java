package eight_puzzle_a_star;

import java.util.ArrayList;

public class Path extends ArrayList<Byte> {
	private Vertex rootVertex;

	public Path(Vertex startVertex) {
		rootVertex = startVertex;
	}

	public Path(Vertex startVertex, Path previousPath, Byte nextNeighborIndex) {
		super(previousPath);
		rootVertex = startVertex;
		this.add(nextNeighborIndex);
	}

	public int getCost() {
		Vertex currentVertex = rootVertex;
		float cost = currentVertex.getHeuristicCost();

		for (Byte edgeIndex : this) { // iterate over all vertices from the root, but not the root
			Vertex vertex = currentVertex.neighbors.get((int)edgeIndex);
			cost += vertex.getHeuristicCost();
		}

		return (int)cost;
	}

	public Vertex getLeafVertex() {
		Vertex currentVertex = rootVertex;

		for (Byte edgeIndex : this) { // iterate over all vertices from the root, but not the root
			Vertex vertex = currentVertex.neighbors.get((int)edgeIndex);
		}

		return currentVertex;
	}
}
