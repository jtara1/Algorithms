package dijkstra_path_finding;

import java.util.Comparator;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.PriorityQueue;

/**
 * A generic implementation of the abstract data type of a graph with directed
 * edge. Intended for use with {@link City} and {@link CityKey}, not tested 
 * with other sets of data or other implementations of {@link Data} and {@link DataKey} 
 * @author James T (jtara1)
 *
 */
public class DirectedGraph {
	/**
	 * The adjacency matrix where each non-null value is the distance / cost from
	 * the source vertex to the destination vertex. A null value indicates there
	 * is no link / edge between the source and destination.
	 */
	private Float[][] graph = null;
	
	/**
	 * Maps a vertex at an index to a {@link DataKey}
	 */
	private Dictionary<Integer, DataKey> indexToDataKeyDict = new Hashtable<Integer, DataKey>();
	
	/**
	 * Maps a {@link DataKey} to a {@link Data}
	 */
	private Dictionary<DataKey, Data> dataKeyToDataDict = new Hashtable<DataKey, Data>();
	
	/**
	 * Load up default data set
	 */
	public DirectedGraph() {
		this(new CityRoadLoader("city.dat", "road.dat"));
	}
	
	/**
	 * Copy structures from loader into attributes of this instance
	 * @param loader
	 */
	public DirectedGraph(DataLoader loader) {
		graph = loader.getAdjMatrix();
		indexToDataKeyDict = loader.getIndexToKey();
		dataKeyToDataDict = loader.getKeyToData();
	}
	
	public Dictionary<Integer, DataKey> getIndexToDataKeyDict() {
		return indexToDataKeyDict;
	}

	public Dictionary<DataKey, Data> getDataKeyToDataDict() {
		return dataKeyToDataDict;
	}
	
	public boolean insertEdge(Data source, Data destination, float distance) {
		return insertEdge(source.getIndex(), destination.getIndex(), distance);
	}

	public boolean insertEdge(DataKey source, DataKey destination, float distance) {
		int src = dataKeyToDataDict.get(source).getIndex();
		int dest = dataKeyToDataDict.get(destination).getIndex();
		
		return insertEdge(src, dest, distance);
	}
	
	/**
	 * Create an edge from source to destination with a cost of distance
	 * @param source The src vertex index
	 * @param destination The dest vertex index
	 * @param distance The cost to traverse along the edge being inserted
	 * @return true if the insertion was successful, false if an edge already 
	 * existed from source to destination
	 */
	public boolean insertEdge(int source, int destination, float distance) {
		if (graph[source][destination] != null)
			return false; // there was already an edge from src to dest
		
		graph[source][destination] = distance;
		return true;
	}
	
	public boolean removeEdge(Data source, Data destination) {
		return removeEdge(source.getIndex(), destination.getIndex());
	}
	
	public boolean removeEdge(DataKey source, DataKey destination) {
		int src = dataKeyToDataDict.get(source).getIndex();
		int dest = dataKeyToDataDict.get(destination).getIndex();
		
		return removeEdge(src, dest);
	}
	
	/**
	 * Removes the edge leading from source vertex to destination vertex
	 * @param source The src vertex index
	 * @param destination The dest vertex
	 * @return false if there was no edge from source to destination to remove, true if 
	 * operation was successful
	 */
	public boolean removeEdge(int source, int destination) {
		if (graph[source][destination] == null)
			return false; // there was already no edge from src to dest
		
		graph[source][destination] = null;
		return true;
	}
	
	public Path dijkstra(DataKey source, DataKey destination) {
		int v1 = dataKeyToDataDict.get(source).getIndex();
		int v2 = dataKeyToDataDict.get(destination).getIndex();
		return dijkstra(v1, v2);
	}
	
	/**
	 * Uses Dijkstra's algorithm to find the shortest path from source to destination
	 * @param source The src vertex index
	 * @param destination The dest vertex index
	 * @return A {@link Path} containing the total distance and a linked list containing the 
	 * sequence of vertices traversed to to travel from source to destination
	 */
	public Path dijkstra(int source, int destination) {
		// total distance from source vertex to vertex at index i
		Float[] distances = new Float[graph.length];
		// parent to vertex at index i that follows the shortest path to get to the source vertex
		Integer[] parents = new Integer[graph.length];
		
		// init destination total distances that're infinity except for source vertex
		for (int i = 0; i < distances.length; i++) {
			distances[i] = Float.POSITIVE_INFINITY;
		}
		distances[source] = 0f;
		
		// Given two indices, compare the total distance of each one to the source vertex 
		class EdgeComparator implements Comparator<Integer> {
			public int compare(Integer obj1, Integer obj2) {
				Float difference = distances[obj1] - distances[obj2];
				if (difference == 0f)
					return 0;
				return difference < 0 ? -1 : 1;
			}
		}
		// the queue used to determine which vertex will be processed next
		PriorityQueue<Integer> sourceQueue = new PriorityQueue<Integer>(distances.length, new EdgeComparator());
		for (int i = 0; i < graph.length; i++) {
			sourceQueue.add(i);
		}
		
		Integer src = null;
		while ((src = sourceQueue.poll()) != null) {
			for (int dest = 0; dest < graph.length; dest++) {
				// src has no edge connecting it to dest
				if (graph[src][dest] == null)
					continue;
				
				// the remaining vertices are inaccessible from source
				if (distances[src] == Float.POSITIVE_INFINITY)
					break;
				
				// relax
				Float distance = distances[src] + graph[src][dest];
				if (distance < distances[dest]) {
					distances[dest] = distance;
					parents[dest] = src;
				}
				// check if the shortest path from source to destination has been found
				if (parents[destination] != null) {
					return new Path(
							parents, 
							source, 
							destination, 
							distances[destination]
					);
				}
			}
		}
		return null;
	}
	
	public Data getData(DataKey key) {
		return dataKeyToDataDict.get(key);
	}
	
	/**
	 * Returns the string representing the adjacency matrix ({@link #graph})
	 */
	@Override
	public String toString() {
		String result = "";
		for (int i = 0; i < graph.length; i++) {
			for (int j = 0; j < graph[0].length; j++) {
				result += graph[i][j] + " ";
			}
			result += "\n";
		}
		return result;
	}
	
	/**
	 * An alternative method to get a string representation of this instance
	 * Calls {@link #toString()} then appends a representation of the {@link #indexToDataKeyDict}
	 * and {@link #dataKeyToDataDict} dictionaries
	 * TODO: Test this out
	 * @return
	 */
	public String toStringDebug() {
		String result = toString();
		
		for (Enumeration<Integer> e = indexToDataKeyDict.keys(); e.hasMoreElements();) {
			Integer key = e.nextElement();
			result += key + ": " + indexToDataKeyDict.get(key) + "\n";
		}
		result += "--------";
		
		for (Enumeration<DataKey> e = dataKeyToDataDict.keys(); e.hasMoreElements();) {
			DataKey key = e.nextElement();
			result += key.toString() + ": " + dataKeyToDataDict.get(key) + "\n";
		}
		return result;
	}
}
