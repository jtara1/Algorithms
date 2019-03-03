package eight_puzzle_a_star;

import java.util.ArrayList;
import java.util.Enumeration;

public class Path implements Enumeration<Vertex> {
	private Vertex rootVertex;
	private Integer totalCost = null;

	private int enumerationIndex = 0;
	private Vertex enumerationVertex = null;

	public ArrayList<Integer> getNeighborIndicesSequence() {
		return neighborIndicesSequence;
	}

	private ArrayList<Integer> neighborIndicesSequence = new ArrayList<>();

	public Path(Vertex startVertex) {
		rootVertex = startVertex;
		totalCost = getCost();
	}

	public Path(Vertex startVertex, Path previousPath, int nextNeighborIndex) {
		this.neighborIndicesSequence = new ArrayList<Integer>(previousPath.getNeighborIndicesSequence());
		rootVertex = startVertex;
		this.neighborIndicesSequence.add(nextNeighborIndex);
		totalCost = getCost();
	}

	public int getCost() {
		if (totalCost != null) return totalCost;

		Vertex currentVertex = rootVertex;
		float cost = currentVertex.getHeuristicCost();

		for (int edgeIndex : neighborIndicesSequence) { // iterate over all vertices from the root, but not the root
			Vertex vertex = currentVertex.neighbors.get((int)edgeIndex);
			if (vertex == null) return Integer.MAX_VALUE;
			cost += vertex.getHeuristicCost();
		}

		return (int)cost;
	}

	public Vertex getLeafVertex() {
		Vertex currentVertex = rootVertex;

		for (int edgeIndex : neighborIndicesSequence) { // iterate over all vertices from the root, but not the root
			currentVertex = currentVertex.neighbors.get((int)edgeIndex);
		}

		return currentVertex;
	}

	// Object overrides
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(totalCost.toString() + ": [ ");

		for (Integer value : neighborIndicesSequence) {
			stringBuilder.append(value.toString() + " ");
		}

		stringBuilder.append("]");
		return stringBuilder.toString();
	}

	public String toStringFullPath() {
		StringBuilder stringBuilder = new StringBuilder();
		Vertex vertex;

		while (this.hasMoreElements()) {
			vertex = this.nextElement();
			stringBuilder.append(vertex.toString() + "\n");
		}

		return stringBuilder.toString();
	}

	// Enumeration implementations
	public boolean hasMoreElements() {
		if (enumerationVertex == null) return true;
		if (enumerationIndex >= neighborIndicesSequence.size()) return false;

		int neighborIndex = neighborIndicesSequence.get(enumerationIndex);
		return enumerationVertex.neighbors.get(neighborIndex) != null;
//		return enumerationIndex < neighborIndicesSequence.size();
	}

	public Vertex nextElement() {
		if (enumerationVertex == null) {
			enumerationVertex = rootVertex;
			return enumerationVertex;
		}

		int neighborIndex = neighborIndicesSequence.get(enumerationIndex++);
		enumerationVertex = enumerationVertex.neighbors.get(neighborIndex);

		return enumerationVertex;

//		Vertex next = rootVertex;
//		for (int i = 0; i < enumerationIndex; ++i) {
//			int neighborIndex = neighborIndicesSequence.get(i);
//			next = next.neighbors.get(neighborIndex);
//		}
//
//		++enumerationIndex;
//		return next;
	}
}
