package eight_puzzle_a_star;

import java.util.ArrayList;
import java.util.Arrays;

public class GameState extends Vertex {
	private ArrayList<Integer> board;

	public static Boolean isSolveableBoard(ArrayList<Integer> board) {
		assert(board.size() == 8);
		int inversions = 0;

		for (int i = 0; i < board.size() - 1; ++i) {
			if (board.get(i) > board.get(i + 1))
				inversions++;
		}

		return inversions % 2 == 0;
	}

	static GameState unsolveableGameState = new GameState(
			new ArrayList<Integer>(
					Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
			)
	);

	public GameState(ArrayList<Integer> board) {
		assert(board.size() == 8);
		this.board = board;
	}

	/* for the 8 puzzle problem, this'll generate the 2-4 neighbors of the vertex & the edges */
	public void generateNeighbors() {

	}
}
