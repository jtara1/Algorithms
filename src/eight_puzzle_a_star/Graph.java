package eight_puzzle_a_star;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.PriorityQueue;

/* the vertices itself implement a method to expand its neighbors */
public class Graph {
	// attrs
	private Vertex rootVertex;
	private PriorityQueue<Path> frontier;
	private HashMap<String, Vertex> vertices;
	private Character depth;

	private long runtimeDuration;

	// gettters & setters
	public Character getDepth() { return depth; }

	// constructors
	public Graph(Vertex startVertex) {
		rootVertex = startVertex;

		frontier = new PriorityQueue<Path>(10000, new PathComparator());
		frontier.add(new Path(rootVertex));

		vertices = new HashMap<String, Vertex>();
		vertices.put(rootVertex.toString(), rootVertex);

		depth = 0;
	}

	public Path aStarExpansion() {
		Vertex leaf;
		Character iterationCount = 0;

		Instant runtimeStart = Instant.now();
		do {
			// of all paths to leaf vertices in the frontier, pop top (smallest path cost given by f(n))
			Path bestPath = frontier.poll();
			if (bestPath == null) return new NullPath(rootVertex);
			leaf = bestPath.getLeafVertex();

			if (leaf.isSolution()) {
				runtimeDuration = Duration.between(runtimeStart, Instant.now()).toSeconds();
				return bestPath;
			}

			// generate the neighbors of this leaf, add these [0, 4] paths to the frontier, repeat
			generatePaths(bestPath, leaf);

			depth++;
		} while (depth < 100000); // 100,000

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

			vertices.put(neighbor.toString(), neighbor);
		}
	}

	// Object overrides
	public String toString() {
		return String.format(
				"Heuristic: %s\nDepth: %d\nVertices: %d\nRuntime (seconds): %d\n",
				rootVertex.getHeuristic().getClass().toString(),
				depth,
				vertices.size(),
				runtimeDuration
		);
	}
}
