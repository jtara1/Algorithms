package dijkstra_path_finding;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * Enables a user to do operations with a directed graph using the 
 * city and raod data set
 * @author j
 *
 */
public class UserInterface {
	Scanner scanner = null;
	DirectedGraph graph = null;
	
	public static void main(String[] args) {
		UserInterface ui = new UserInterface();
		ui.start();
	}
	
	/**
	 * Start the UI 
	 */
	public void start() {
		scanner = new Scanner(System.in);
		graph = new DirectedGraph();
		commandMenu();
	}
	
	/**
	 * Allows user to do operations with the graph
	 */
	private void commandMenu() {
		String[] codes;
		City src, dest;
		String menu = "Q Query the city information by entering the city code.\n"
				+ "D Find the minimum distance between two cities.\n"
				+ "I Insert a road by entering two city codes and distance.\n"
				+ "R Remove an existing road by entering two city codes.\n"
				+ "H Display this message.\n"
				+ "E Exit.";
		
		while (true) {
			System.out.print("Enter command: ");
			String input = scanner.nextLine().trim().toUpperCase();
			
			switch (input) {
			case "Q":
				// query, get city data from dictionary, cast to City, print
				codes = getCityCodes(1);
				System.out.println(getCity(codes[0]));
				break;
			case "D":
				// distance between two cities
				codes = getCityCodes(2);
				CityKey key1 = new CityKey(codes[0]);
				CityKey key2 = new CityKey(codes[1]);
				Path path = graph.dijkstra(key1, key2);
				
				if (path == null) {
					System.out.printf("There is no path from %s to %s.", key1.toString(), key2.toString());
				} else {
					printPath(path, key1, key2);
				}
				break;
			case "I":
				// insert road (edge)
				codes = getCityCodes(3);
				src = getCity(codes[0]);
				dest = getCity(codes[1]);
				Float dist = Float.parseFloat(codes[2]);
				
				// if insertion unsuccessful
				if (!graph.insertEdge(src, dest, dist)) {
					System.out.printf("There is already a road from the %s to %s.\n", src.getName(), dest.getName());
				} else {
					// insertion successful
					System.out.printf("You inserted a road from %s to %s with distance of %.2f.\n", 
							src.getName(),
							dest.getName(),
							dist
						);
				}
				break;
			case "R":
				// rm road (edge)
				codes = getCityCodes(2);
				src = getCity(codes[0]);
				dest = getCity(codes[1]); 
				
				// rm unsuccessful
				if (!graph.removeEdge(src, dest)) {
					System.out.printf("The road from %s to %s doesn't exist.\n", src.getName(), dest.getName());
				} else {
					// rm successful
					System.out.printf("The road from %s to %s was removed.\n", src.getName(), dest.getName());
				}
				break;
			case "H":
				// display help menu
				System.out.println(menu);
				break;
			case "E":
				// exit
				System.exit(0);
			default:
				// invalid input
				System.out.println("Invalid input");
				break;
			}
		}
	}
	
	/**
	 * Gets number of words (based on inputs argument) from console input
	 * @param numberOfInputs
	 * @return The array of size based on argument inputs each element being a word
	 * entered by the user
	 */
	private String[] getCityCodes(int numberOfInputs) {
		String[] codes = new String[numberOfInputs];
		System.out.print("City code" + (numberOfInputs == 1 ? ": " : "s: "));
		for (int i = 0; i < numberOfInputs; i++) {
			codes[i] = scanner.next().toUpperCase();
		}
		scanner.nextLine(); // clear the newline character
		return codes;
	}
	
	/**
	 * Print the src and destination cities, and the sequence of vertices visited
	 * to traverse the path
	 * @param path
	 * @param source
	 * @param destination
	 */
	private void printPath(Path path, CityKey source, CityKey destination) {
		City city1 = (City)graph.getDataKeyToDataDict().get(source);
		City city2 = (City)graph.getDataKeyToDataDict().get(destination);
		
		System.out.printf(
				"Minimum distance between %s and %s is %.2f through the route: ",
				city1.getName(),
				city2.getName(),
				path.getDistance()
		);
		
		LinkedList<Integer> route = path.getPath();
		
		while (!route.isEmpty()) {
			Integer index = route.removeFirst();
			CityKey cityCode = (CityKey)graph.getIndexToDataKeyDict().get(index);
			System.out.printf("%s" + (route.isEmpty() ? "\n" : ", "), cityCode.getCode());
		}
	}
	
	/**
	 * Simplifies access to get a {@link City} given the String representing
	 * the {@link CityKey}
	 * @param cityCode
	 * @return
	 */
	private City getCity(String cityCode) {
		return (City)graph.getData(new CityKey(cityCode));
	}
}
