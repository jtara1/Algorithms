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
					randomBoardSolver();
			}
		}
	}

	public String randomBoardSolve() {
		ArrayList<Byte> board = GameState.generateBoard();
		Graph graph = new Graph(new GameState(board, misplacedTilesHeuristic));
		Path path = graph.aStarExpansion();

		Graph graph2 = new Graph(new GameState(board, distanceHeuristic));
		Path path2 = graph2.aStarExpansion();


	}
}
