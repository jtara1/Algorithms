package eight_puzzle_a_star;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
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
	public Vertex getRootVertex() { return rootVertex; }
	public long getRuntimeDuration() { return runtimeDuration; }
	public HashMap<String, Vertex> getVertices() { return vertices; }

	// static
	public static int randomBoardGeneratedCount = 0;
	public static boolean debug = true;

	public static GraphAndPathCollection solveSolveable8Puzzle(Heuristic heuristic) {
		++randomBoardGeneratedCount;

		ArrayList<Byte> board = GameState.generateBoard();
		Path path = new NullPath();

		do {
			if (!GameState.isSolveableBoard(board)) continue;

			Graph graph = new Graph(new GameState(board, heuristic));
			path = graph.aStarExpansion();

			if (path.getClass() == NullPath.class) {
				continue;
			}

			System.out.println("generated solveable random board, # " + randomBoardGeneratedCount);
			return new GraphAndPathCollection(graph, path, board);
		} while (true);
	}

	public static GraphAndPathCollection solveBoard(Heuristic heuristic, ArrayList<Byte> board) {
		Graph graph = new Graph(new GameState(board, heuristic));
		Path path = graph.aStarExpansion();
		return new GraphAndPathCollection(graph, path, board);
	}

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
		if (!rootVertex.isValidVertex()) return new NullPath(rootVertex, "invalid vertex given");
		Vertex leaf;
		Path path;

		Instant runtimeStart = Instant.now();
		do {
			// of all paths to leaf vertices in the frontier, pop top (smallest path cost given by f(n))
			path = frontier.poll();
			if (path == null)
				return new NullPath(rootVertex);
			leaf = path.getLeafVertex();

			if (leaf.isSolution()) {
				runtimeDuration = Duration.between(runtimeStart, Instant.now()).toSeconds();
				return path;
			}

			// generate the neighbors of this leaf, add these [0, 4] paths to the frontier, repeat
			generatePaths(path, leaf);

			depth++;
//		} while (path.getNeighborIndicesSequence().size() <= 25);
		} while (depth < 100000); // 100,000

		System.out.println("warning: very large depth reached when generating tree for solution search");
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
				"\nHeuristic: %s\nDepth: %d\nVertices: %d\nRuntime (seconds): %d\n",
				rootVertex.getHeuristic().getClass().toString(),
				(int)depth,
				vertices.size(),
				runtimeDuration
		);
	}
}
