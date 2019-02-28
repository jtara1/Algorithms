package eight_puzzle_a_star;

import java.util.ArrayList;

/**
 * Represents a vertex in a graph. It must implement a method to get a value to
 */
public abstract class Vertex {
	protected ArrayList<Vertex> neighbors;

	public abstract void generateNeighbors();

	public abstract float getHeuristicCost(Heuristic heuristic);
	public abstract float getHeuristicCost();
}
