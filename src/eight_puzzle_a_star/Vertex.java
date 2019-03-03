package eight_puzzle_a_star;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a vertex in a graph. It must implement a method to get a value to
 */
public abstract class Vertex {
	// attributes
	public ArrayList<Vertex> neighbors;
	public float pathCost = 0;

	// getters & setters
	public abstract int getNeighborsSize();
	public abstract Heuristic getHeuristic();

	// methods
	public abstract void generateNeighbors(HashMap<String, Vertex> vertices);
	public abstract float getHeuristicCost();
	public abstract boolean isSolution();

	/**
	 * @param obj
	 * @return
	 */
	public abstract boolean equals(Object obj);

	/**
	 * Because a subclass (or an attribute of it) of this interface will be
	 * a unique identifier associated this vertex
	 * we need a unique identifier for support for use in a Hashtable
	 *
	 * @return This should call Objects.hash(Object objs ...) to get a hash int and
	 * return that hash
	 */
	public abstract int hashCode();

}
