package dijkstra_path_finding;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Loads the cities and roads and sets up the data structures needed for
 * {@link DirectedGraph}
 * @author j
 *
 */
public class CityRoadLoader implements DataLoader {
	/**
	 * Maps an integer index to a {@link DataKey}
	 */
	private Dictionary<Integer, DataKey> indexToKeyDict = new Hashtable<Integer, DataKey>();
	
	/**
	 * Maps a {@link DataKey} to a {@link Data}
	 */
	private Dictionary<DataKey, Data> keyToDataDict = new Hashtable<DataKey, Data>();
	
	/**
	 * Adjacency matrix used to hold the the distances and connections of the vertices
	 * in the graph
	 */
	private Float[][] adjMatrix = null;
	
	CityRoadLoader() {
		this("city.dat", "road.dat");
	}
	
	CityRoadLoader(String citiesFileName, String roadsFileName) {
		loadVerticesFromFile(citiesFileName);
		
		// create the adj matrix now that we know the number of vertices
		int vertices = indexToKeyDict.size(); 
		adjMatrix = new Float[vertices][vertices];
		
		loadEdgesFromFile(roadsFileName);
	}
	
	public Dictionary<Integer, DataKey> getIndexToKey() {
		return indexToKeyDict;
	}
	
	public Dictionary<DataKey, Data> getKeyToData() {
		return keyToDataDict;
	}
	
	public Float[][] getAdjMatrix() {
		return adjMatrix;
	}
	
	/**
	 * Uses regex to matrix the input from the file
	 * Matches the following e.g.: 
	 *   1 LV LAS VEGAS 10000 1000
	 * 987   LA     LAS ANGELES     10000    1111
	 * 100   MF    MT. FUJI    1000    1234.29
	 * @param fileName
	 */
	private void loadVerticesFromFile(String fileName) {
		// match groups 0-5: index, code, name, population, elevation
		Pattern pattern = Pattern.compile(
				"\\s*?(\\d+)\\s+([a-zA-Z][a-zA-Z])\\s+([a-zA-Z]+\\.?\\ ?[a-zA-Z]*?)\\s*(\\d+)\\s+(\\d+\\.?\\d*?).*?"
				);
		
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(fileName));
			String line;
			Matcher matcher;
			
			// read each line
			while (reader.ready() && (line = reader.readLine()) != null) {
				if (line.equals(""))
					break;
				
				matcher = pattern.matcher(line);
				matcher.find();
				City newCity = new City(
						Integer.parseInt(matcher.group(1)) - 1,
						matcher.group(2).toUpperCase().trim(),
						matcher.group(3).trim(),
						Integer.parseInt(matcher.group(4)),
						Float.parseFloat(matcher.group(5))
						);
				CityKey newCityKey = new CityKey(newCity.getCode());
				
				// check if index is already in the dictionary
				if (indexToKeyDict.get(newCity.getIndex()) != null) {
					throw new IllegalArgumentException("duplicate index given in city data input file");
				}
				indexToKeyDict.put(newCity.getIndex(), newCityKey);
				
				// check if the CityKey is already in the dictionary
				if (keyToDataDict.get(newCityKey) != null) {
					reader.close();
					throw new IllegalArgumentException("duplicate city code given in city data input file");
				}
				keyToDataDict.put(newCityKey, newCity);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Uses regex to match groups for each line to load the edge of the graph
	 * matches e.g.:
	 *     5 2 400.4
	 * 40012 3 230
	 * @param fileName
	 */
	private void loadEdgesFromFile(String fileName) {
		BufferedReader reader = null;
		// pattern to get three matches groups (two ints and one int or float) with any amount of white space between
		Pattern pattern = Pattern.compile("\\ *?(\\d+)\\s+(\\d+)\\s+([0-9]+\\.?[0-9]*?)\\s*?");
		
		try {
			reader = new BufferedReader(new FileReader(fileName));
			String line;
			
			// read each line using the regex pattern to match values
			while (reader.ready() && (line = reader.readLine()) != null) {
				if (line.equals(""))
					break;
				
				Matcher matcher = pattern.matcher(line);
				matcher.find();
				
				int v1 = Integer.parseInt(matcher.group(1)) - 1;
				int v2 = Integer.parseInt(matcher.group(2)) - 1;
				Float distance = Float.parseFloat(matcher.group(3));
				
				if (adjMatrix[v1][v2] != null) {
					reader.close();
					throw new IllegalArgumentException("an edge was already given for the two vertices");
				}
				
				adjMatrix[v1][v2] = distance;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
