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
		Vertex leaf;
		Character iterationCount = 0;

		do {
			iterationCount++;

			// of all paths to leaf vertices in the frontier, pop top (smallest path cost given by f(n))
			Path bestPath = frontier.poll();
			if (bestPath == null) return new NullPath(rootVertex);
			leaf = bestPath.getLeafVertex();

			if (leaf.isSolution()) return bestPath;

			// generate the neighbors of this leaf, add these [2, 4] paths to the frontier, repeat
			generatePaths(bestPath, leaf);
		} while (iterationCount < 30000);

		return new NullPath(rootVertex, "max depth for generating graph reached");
	}

	private void generatePaths(Path existingPath, Vertex leaf) {
		leaf.generateNeighbors(vertices);

		for (int index = 0; index < leaf.getNeighborsSize(); ++index) {
			Vertex neighbor = leaf.neighbors.get(index);
			if (neighbor == null || neighbor.getHeuristicCost() == Float.POSITIVE_INFINITY)
				continue;

			Path newPath = new Path(rootVertex, existingPath, index);
			frontier.add(newPath);
		}
	}
}
