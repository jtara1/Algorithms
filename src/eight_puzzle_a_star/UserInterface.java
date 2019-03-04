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
	 * 2: user enters a game board, it gets solved & same output sent to std out
	 */
	public void start() {
		boolean exit = false;
		System.out.println("Sliding 8-Puzzle Solver AI");

		// 0: random board, 1: user enters board, 2: exit
		String prompt = "Select an option:\n[0] generate random board & solve using both heuristics\n[1] enter an initial game state\n[2] exit";

		while (!exit) {
			System.out.println(prompt);
			int option = Integer.parseInt(scanner.nextLine());

			switch(option) {
				case(0):
					System.out.println(randomBoardSolver());
					break;
				case(1):
					System.out.println(getBoardFromUser());
					break;
				case(2):
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

	private String randomBoardSolver() {
		System.out.println("beginning to find valid board & solve with heuristic 1");
		GraphAndPathCollection collection = Graph.solveSolveable8Puzzle(misplacedTilesHeuristic);

		System.out.println("initial board: " + collection.board.toString());

		System.out.println("beginning to solve with heuristic 2");
		Graph graph2 = new Graph(new GameState(collection.board, distanceHeuristic));
		Path path2 = graph2.aStarExpansion();
		GraphAndPathCollection collection2 = new GraphAndPathCollection(graph2, path2, collection.board);

		return getGraphAndPathResults(collection, collection2);
	}

	private String getBoardFromUser() {
		String input = "";
		while (input.equals("")) {
			input = removeCRLF(scanner.nextLine());
		}
		ArrayList<Byte> board = new ArrayList<>();

		for (String numb : input.split(" ")) {
			board.add(Byte.parseByte(numb));
		}

		Graph graph = new Graph(new GameState(board, misplacedTilesHeuristic));
		Path path = graph.aStarExpansion();
		GraphAndPathCollection collection1 = new GraphAndPathCollection(graph, path, board);

		Graph graph2 = new Graph(new GameState(board, distanceHeuristic));
		Path path2 = graph2.aStarExpansion();
		GraphAndPathCollection collection2 = new GraphAndPathCollection(graph2, path2, board);

		return getGraphAndPathResults(collection1, collection2);
	}

	private String removeCRLF(String string) {
		return string.replace("\n", "").replace("\r", "");
	}
}
