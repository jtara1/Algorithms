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
	private Float[] graph = null;
	
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
	
	private void createAdjacencyMatrixFromFile(String fileName) throws IOException {
		BufferedReader in = null;
		int ints = 0;
		// pattern to get three matches groups (two ints and one int or float) with any amount of white space between
		Pattern pattern = Pattern.compile("(\\d*)\\s*(\\d*)\\s*(\\p{Digit}.?\\p{Digit}?)");
		
		try {
			in = new BufferedReader(new FileReader(fileName));
			
			int nextInt;
			ints = 0;

			while (in.ready()) {
				String line = in.readLine();
				ints++;
				
				Matcher matcher = pattern.matcher(line);
				int v1 = Integer.parseInt(matcher.group(0));
				int v2 = Integer.parseInt(matcher.group(1));
				Float distance = Float.parseFloat(matcher.group(2));
				
//				graph[v1][v2] = distance;
//				graph[v2][v1] = distance;
				
				
				// parse individual line
				for (String numb : line.split(" *")) {
					
					System.out.println(numb);
				}
//				System.out.println(line);
			}
		} catch (EOFException e) {
			System.out.println(ints);
		} catch (IOException e) {
			
		} finally {
			if (in != null) {
				in.close();
			}
		}
		
	}
}
