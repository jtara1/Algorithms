package eight_puzzle_a_star;

import java.util.HashMap;
import java.util.PriorityQueue;

/* the vertices itself implement a method to expand its neighbors */
public class Graph {
	private Vertex rootVertex;
	private PriorityQueue<Path> frontier;
	private HashMap<String, Vertex> vertices;

	public Graph(Vertex startVertex) {
		rootVertex = startVertex;

		frontier = new PriorityQueue<Path>(10000, new PathComparator());
		frontier.add(new Path(rootVertex));

		vertices = new HashMap<String, Vertex>();
		vertices.put(rootVertex.toString(), rootVertex);
	}

	public Path aStarExpansion() {
		// of all paths to leaf vertices in the frontier, pop top (smallest path cost given by f(n))
		Path bestPath = frontier.remove();
		if (bestPath.getLeafVertex().isSolution()) return bestPath;

		// generate the neighbors of this leaf, add these [2, 4] paths to the frontier, repeat
	}
}
