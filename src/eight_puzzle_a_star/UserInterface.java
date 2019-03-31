package eight_puzzle_a_star;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class UserInterface {
	private Scanner scanner = new Scanner(System.in);

	private MisplacedTilesHeuristic misplacedTilesHeuristic = new MisplacedTilesHeuristic();
	private DistanceHeuristic distanceHeuristic = new DistanceHeuristic();

	// static
	public static void main(String[] args) {
		UserInterface ui = new UserInterface();
		ui.start();
	}

	/**
	 * user gets two options
	 * 1: run algo using both heuristics & output path, the solution depth,
	 * the search cost for each heuristic, and the time it took
	 * 2: user enters a game board_pointer, it gets solved & same output sent to std out
	 */
	public void start() {
		boolean exit = false;
		System.out.println("Sliding 8-Puzzle Solver AI");

		// 0: random board_pointer, 1: user enters board_pointer, 2: exit
		String prompt = "Select an option:\n[0] generate random board_pointer with depth == 20 & solve using both heuristics\n[1] enter an initial game state\n[2] generate random board_pointer & solve\n[3] exit";

		while (!exit) {
			System.out.println(prompt);
			int option = Integer.parseInt(scanner.nextLine());

			switch(option) {
				case(0):
					System.out.println(randomBoardSolver(false));
					break;
				case(1):
					System.out.println(getBoardFromUser());
					break;
				case(2):
					System.out.println(randomBoardSolver(true));
					break;
				case(3):
					return;
			}
		}
	}

	private String getGraphAndPathResults(GraphAndPathCollection collection1, GraphAndPathCollection collection2) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("optimal path, misplaced titles heuristc\n");
		stringBuilder.append(collection1.path.toStringFullPath());

		stringBuilder.append("optimal path, distance heuristc\n");
		stringBuilder.append(collection2.path.toStringFullPath());

		stringBuilder.append(collection1.toStringResults());
		stringBuilder.append(collection2.toStringResults());

		return stringBuilder.toString();
	}

	private String randomBoardSolver(boolean randomBoard) {
		System.out.println("beginning to find valid board_pointer & solve with heuristic 1");
		GraphAndPathCollection collection = Graph.solveSolveable8Puzzle(misplacedTilesHeuristic, randomBoard);

		System.out.println("initial board_pointer: " + collection.board_pointer.toString());

		System.out.println("beginning to solve with heuristic 2");
		Graph graph2 = new Graph(new GameState(collection.board_pointer, distanceHeuristic));
		Path path2 = graph2.aStarExpansion();
		GraphAndPathCollection collection2 = new GraphAndPathCollection(graph2, path2, collection.board_pointer);

		return getGraphAndPathResults(collection, collection2);
	}

	private String getBoardFromUser() {
		String input = "";
		while (input.equals("")) {
			input = removeCRLF(scanner.nextLine());
		}
		ArrayList<Byte> board_pointer = new ArrayList<>();

		for (String numb : input.split(" ")) {
			board_pointer.add(Byte.parseByte(numb));
		}

		Graph graph = new Graph(new GameState(board_pointer, misplacedTilesHeuristic));
		Path path = graph.aStarExpansion();
		GraphAndPathCollection collection1 = new GraphAndPathCollection(graph, path, board_pointer);

		Graph graph2 = new Graph(new GameState(board_pointer, distanceHeuristic));
		Path path2 = graph2.aStarExpansion();
		GraphAndPathCollection collection2 = new GraphAndPathCollection(graph2, path2, board_pointer);

		return getGraphAndPathResults(collection1, collection2);
	}

	private String removeCRLF(String string) {
		return string.replace("\n", "").replace("\r", "");
	}
}
