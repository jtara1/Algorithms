package dijkstra_path_finding;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CityRoadLoader implements DataLoader {
//	private class Road {
//		public int source;
//		public int destination;
//		public Float cost;
//		Road(int src, int dest, Float distance) {
//			source = src;
//			destination = dest;
//			cost = distance;
//		}
//	}
	
	private Dictionary<Integer, DataKey> indexToKeyDict = new Hashtable<Integer, DataKey>();
	private Dictionary<DataKey, Data> keyToDataDict = new Hashtable<DataKey, Data>();
	private Float[][] adjMatrix = null;
	
	CityRoadLoader() {
		this("city.dat", "road.dat");
	}
	
	CityRoadLoader(String citiesFileName, String roadsFileName) {
		loadVerticesFromFile(citiesFileName);
		
		int vertices = indexToKeyDict.size(); 
		adjMatrix = new Float[vertices][vertices];
		
		loadEdgesFromFile(roadsFileName);
	}
	
	public Dictionary<Integer, DataKey> getIndexToKeyDict() {
		return indexToKeyDict;
	}
	
	public Dictionary<DataKey, Data> getKeyToData() {
		return keyToDataDict;
	}
	
	public Float[][] getAdjMatrix() {
		return adjMatrix;
	}
	
	private void loadVerticesFromFile(String fileName) {
		// match groups 0-5: index, code, name, population, elevation
		Pattern pattern = Pattern.compile(
				"\\s*?(\\d+)\\s+([a-zA-Z][a-zA-Z])\\s+([a-zA-Z]+\\.?\\ ?[a-zA-Z]*?)\\s+(\\d+)\\s+(\\d+\\.?\\d*?).*?"
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
						matcher.group(2).toUpperCase(),
						matcher.group(3),
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
					throw new IllegalArgumentException("duplicate city code given in city data input file");
				}
				keyToDataDict.put(newCityKey, newCity);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
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
