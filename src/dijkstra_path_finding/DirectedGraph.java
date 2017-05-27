package dijkstra_path_finding;

import java.util.Comparator;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.PriorityQueue;

public class DirectedGraph {
	private Float[][] graph = null;
	
	private Dictionary<Integer, DataKey> indexToDataKeyDict = new Hashtable<Integer, DataKey>();
	private Dictionary<DataKey, Data> dataKeyToDataDict = new Hashtable<DataKey, Data>();
	
	protected interface PathPrinter extends Path.Printer {};
	
	public DirectedGraph() {
		this(new CityRoadLoader("city.dat", "road.dat"));
//		String cityDataFileName = "city.dat";
//		String edgeDataFileName = "road.dat";
	}
	
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

	public boolean insertEdge(DataKey source, DataKey destination, float distance) {
		int src = dataKeyToDataDict.get(source).getIndex();
		int dest = dataKeyToDataDict.get(destination).getIndex();
		
		return insertEdge(src, dest, distance);
	}
	
	public boolean insertEdge(int source, int destination, float distance) {
		if (graph[source][destination] != null)
			return false; // there was already an edge from src to dest
		
		graph[source][destination] = distance;
		return true;
	}
	
	public boolean removeEdge(DataKey source, DataKey destination) {
		int src = dataKeyToDataDict.get(source).getIndex();
		int dest = dataKeyToDataDict.get(destination).getIndex();
		
		return removeEdge(src, dest);
	}
	
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
