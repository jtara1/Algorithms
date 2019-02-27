package eight_puzzle_a_star;

import java.util.ArrayList;

/**
 * Represents a vertex in a graph. It must implement a method to get a value to
 */
public abstract class Vertex {
	protected ArrayList<Edge> edges;

	public abstract void generateNeighbors();
}
