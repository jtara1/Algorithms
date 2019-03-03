package eight_puzzle_a_star;

import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
	private Scanner scanner = new Scanner(System.in);

	private MisplacedTilesHeuristic misplacedTilesHeuristic = new MisplacedTilesHeuristic();
	private DistanceHeuristic distanceHeuristic = new DistanceHeuristic();

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
			int option = scanner.nextInt();

			switch(option) {
				case(0):
					System.out.println(randomBoardSolver());
					break;
			}
		}
	}

	public String randomBoardSolver() {
		StringBuilder stringBuilder = new StringBuilder();
		System.out.println("beginning to find valid board & solve with heuristic 1");
		GraphAndPathCollection collection = Graph.solveSolveable8Puzzle(misplacedTilesHeuristic);

		System.out.println("initial board: " + collection.board.toString());

		System.out.println("beginning to solve with heuristic 2");
		Graph graph2 = new Graph(new GameState(collection.board, distanceHeuristic));
		Path path2 = graph2.aStarExpansion();

		stringBuilder.append("optimal path, misplaced titles heuristc\n");
		stringBuilder.append(collection.path.toStringFullPath());
		stringBuilder.append(collection.graph.toString());
		stringBuilder.append("Path Cost: " + collection.path.getTotalCost() + "\n");

		stringBuilder.append("optimal path, distance heuristc\n");
		stringBuilder.append(path2.toStringFullPath());
		stringBuilder.append(graph2.toString());
		stringBuilder.append("Path Cost: " + path2.getTotalCost() + "\n");

		return stringBuilder.toString();
	}
}
