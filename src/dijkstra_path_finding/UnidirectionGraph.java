package dijkstra_path_finding;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class UnidirectionGraph {
	private Float[][] graph = null;
	
	private Dictionary<Integer, DataKey> indexToDataKeyDict = new Hashtable<Integer, DataKey>();
	private Dictionary<DataKey, Data> dataKeyToDataDict = new Hashtable<DataKey, Data>();
	
	public UnidirectionGraph() {
		this(new CityRoadLoader("city.dat", "road.dat"));
//		String cityDataFileName = "city.dat";
//		String edgeDataFileName = "road.dat";
	}
	
	public UnidirectionGraph(DataLoader loader) {
		graph = loader.getAdjMatrix();
		indexToDataKeyDict = loader.getIndexToKeyDict();
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
