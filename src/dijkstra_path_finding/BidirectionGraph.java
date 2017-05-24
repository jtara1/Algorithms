package dijkstra_path_finding;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class BidirectionGraph {
	private Float[][] graph = null;
	
	private Dictionary<Integer, DataKey> indexToDataKeyDict = new Hashtable<Integer, DataKey>();
	private Dictionary<DataKey, Data> dataKeyToDataDict = new Hashtable<DataKey, Data>();
	
	public BidirectionGraph(Dictionary<Integer, DataKey> indexToDataKey, Dictionary<DataKey, Data> dataKeyToData, String edgeDataFileName) {
		indexToDataKeyDict = indexToDataKey;
		dataKeyToDataDict = dataKeyToData;
		try {
			createAdjacencyMatrixFromFile(edgeDataFileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BidirectionGraph() {
		String cityDataFileName = "city.dat";
		String edgeDataFileName = "road.dat";
		try {
			createAdjacencyMatrixFromFile(edgeDataFileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BidirectionGraph(DataLoader loader) {
		graph = loader.getAdjMatrix();
		indexToDataKeyDict = loader.getIndexToKeyDict();
		dataKeyToDataDict = loader.getKeyToData();
	}
	
	private void createAdjacencyMatrixFromFile(String fileName) throws IOException {

	}
}
