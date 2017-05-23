package dijkstra_path_finding;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CityLoader implements DataLoader {
	Dictionary<Integer, DataKey> indexToKeyDict = new Hashtable<Integer, DataKey>();
	Dictionary<DataKey, Data> keyToDataDict = new Hashtable<DataKey, Data>();
	
	CityLoader() {
		
	}
	
	public void loadAllFromFile(String fileName) {
		ArrayList<City> cities = new ArrayList<City>();
		// regex to match values in a line
		Pattern pattern = Pattern.compile(
				"(\\d*)\\p{Space}*" // group 0
				+ "(2{\\p{Alpha}})\\p{Space}*" // group 1
				+ "(\\p{Alpha}*\\.?\\ ?\\p{Alpha}*?)\\p{Space}*" // group 2
				+ "(\\d*)\\p{Space}*" // group 3
				+ "(\\d*\\.?\\d*?)" // group 4
				);
		
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(fileName));
			
			// read each line
			while (reader.ready()) {
				Matcher matcher = pattern.matcher(reader.readLine());
				City newCity = new City(
						Integer.parseInt(matcher.group(0)),
						matcher.group(1),
						matcher.group(2),
						Integer.parseInt(matcher.group(3)),
						Float.parseFloat(matcher.group(4))
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
