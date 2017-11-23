package dijkstra_path_finding;

/**
 * Contains the data associated with a single vertex in the graph.
 * @author j
 *
 */
public abstract class Data {
	/**
	 * The index of the vertex in the graph
	 */
	protected int index;
	
	/**
	 * @return The index
	 */
	public abstract int getIndex();
}
