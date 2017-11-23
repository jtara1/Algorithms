package dijkstra_path_finding;

import java.util.Dictionary;

/**
 * Loads data from files and returns needed structures
 * for {@link DirectedGraph}
 * @author j
 *
 */
public interface DataLoader {
	public abstract Dictionary<Integer, DataKey> getIndexToKey();
	public abstract Dictionary<DataKey, Data> getKeyToData();
	
	/**
	 * Two dimensional array containing distances from source vertex
	 * to destination vertex. If there is no edge from one vertex to another then 
	 * the value at that index would be null 
	 * @return
	 */
	public abstract Float[][] getAdjMatrix();
}
