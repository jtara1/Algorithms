package eight_puzzle_a_star;

import java.util.PriorityQueue;

/* the vertices itself implement a method to expand its neighbors */
public class Graph {
	private Vertex rootVertex;
	private PriorityQueue<Vertex> frontier;

	public Graph(Vertex startVertex) {
		rootVertex = startVertex;
		frontier = new PriorityQueue<>(10000);
		frontier.add(startVertex);
	}

	public void aStarExpansion() {
		// of all paths to leaf vertices in the frontier, pop top (smallest path cost given by f(n))
		// generate the neighbors of this leaf, add these [2, 4] paths to the frontier, repeat
	}
}
