package eight_puzzle_a_star;

import java.util.ArrayList;

public class UnitTest {
	private Heuristic misplacedTilesHeuristic = new MisplacedTilesHeuristic();
	private Heuristic distanceHeuristic = new DistanceHeuristic();

	public static void main(String[] args) {
		UnitTest suite = new UnitTest();
//		suite.case1();
//		suite.unsolveableBoardCase2();
//		suite.case3();
//		suite.case4();
//		suite.case5();
		suite.case6();
	}

	public void case1() {
		Graph graph = new Graph(new GameState(misplacedTilesHeuristic, 3, 1, 2, 4, 0, 5, 6, 7, 8));
		Path path = graph.aStarExpansion();

		System.out.println(path.toStringFullPath());

		assert(path.getLeafVertex().isSolution());
	}

	public void unsolveableBoardCase2() {
		// this board_pointer seems solveable, but it can only move 0 left, or down which both lead to unsolveable boards
		Graph graph = new Graph(new GameState(misplacedTilesHeuristic, 2, 1, 0, 3, 4, 5, 6, 7, 8));
		Path path = graph.aStarExpansion();

		System.out.println(path.toStringFullPath());

//		assert(path.getClass() == NullPath.class);
	}

	public void case3() {
		// {1, 5, 7, 0, 4, 8, 6, 2, 3} // not solveable
		Graph graph = new Graph(new GameState(distanceHeuristic, 1, 5, 7, 0, 4, 8, 6, 2, 3));
		Path path = graph.aStarExpansion();

		System.out.println(path.toStringFullPath());
	}

	public void case4() {
		// [4, 7, 1, 5, 2, 0, 6, 8, 3]
		Path path = runTest(misplacedTilesHeuristic, 4, 7, 1, 5, 2, 0, 6, 8, 3);
	}

	private void case5() {
		Path path = runTest(misplacedTilesHeuristic, 0, 1, 2, 3, 4, 5, 6, 8, 7);
	}

	private void case6() {
		runTest(misplacedTilesHeuristic, 1, 0, 2, 3, 5, 4, 6, 7, 8);
	}

	private Path runTest(Heuristic heuristic, int... tileStates) {
		ArrayList<Byte> board_pointer = new ArrayList<>(9);
		for (int state : tileStates) {
			board_pointer.add((byte)state);
		}

		Graph graph = new Graph(new GameState(board_pointer, heuristic));
		Path path = graph.aStarExpansion();

		System.out.println(path.toStringFullPath());

		return path;
	}
}
