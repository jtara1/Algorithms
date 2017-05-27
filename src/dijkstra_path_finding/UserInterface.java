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
				Data city = graph.getDataKeyToDataDict().get(new CityKey(inputValues.get(1)));
				System.out.println((City)city);
				break;
			case "D":
				// distance between two cities
				Path path = graph.dijkstra(
						new CityKey(inputValues.get(1)), 
						new CityKey(inputValues.get(2))
						);
				
				if (path == null) {
					System.out.printf("There is no path from %s to %s.", inputValues.get(1), inputValues.get(2));
				} else {
					printPath(path, new CityKey(inputValues.get(1)), new CityKey(inputValues.get(2)));
				}
				break;
			case "I":
				// insert road (edge)
				if (!graph.insertEdge(
						new CityKey(inputValues.get(1)), 
						new CityKey(inputValues.get(2)), 
						Float.parseFloat(inputValues.get(3))
						)) {
					System.out.printf("There is already a road from the %s to %s.\n", inputValues.get(1), inputValues.get(2));
				}
				break;
			case "R":
				// rm road (edge)
				if (!graph.removeEdge(
						new CityKey(inputValues.get(1)), 
						new CityKey(inputValues.get(2))
						)) {
					System.out.printf("The road from %s to %s doesn't exist.\n", inputValues.get(1), inputValues.get(2));
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
}
