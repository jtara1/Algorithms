package dijkstra_path_finding;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class UserInterface {
	Scanner scanner = new Scanner(System.in);
	DirectedGraph graph = null;
	
	public static void main(String[] args) {
		UserInterface ui = new UserInterface();
		ui.start();
	}
	
	public void start() {
		graph = new DirectedGraph();
		commandMenu();
	}
	
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
			
			ArrayList<String> inputValues = new ArrayList<String>();
			for (String value : input.split(" ")) {
				inputValues.add(value);
			}
			
			switch (inputValues.get(0)) {
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
					System.out.printf("There is no path from %s to %s.", inputValues.get(1), inputValues.get(2));
				} else {
					printPath(path, new CityKey(codes[0]), new CityKey(codes[1]));
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
				
				if (!graph.removeEdge(src, dest)) {
					System.out.printf("The road from %s to %s doesn't exist.\n", src.getName(), dest.getName());
				} else {
					System.out.printf("The road from %s to %s was inserted.\n", src.getName(), dest.getName());
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
	
	private String[] getCityCodes(int inputs) {
		String[] codes = new String[inputs];
		System.out.print("City code" + (inputs == 1 ? ": " : "s: "));
		for (int i = 0; i < inputs; i++) {
			codes[i] = scanner.next().toUpperCase();
		}
		scanner.nextLine(); // clear the newline character
		return codes;
	}
	
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
	
	private City getCity(String cityCode) {
		return (City)graph.getData(new CityKey(cityCode));
	}
}
