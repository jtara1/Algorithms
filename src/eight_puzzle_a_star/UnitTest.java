package eight_puzzle_a_star;

public class UnitTest {
	private Heuristic misplacedTilesHeuristic = new MisplacedTilesHeuristic();
	private Heuristic distanceHeuristic = new DistanceHeuristic();

	public static void main(String[] args) {
		UnitTest suite = new UnitTest();
//		suite.case1();
		suite.unsolveableBoardCase2();
	}

	public void case1() {
		Graph graph = new Graph(new GameState(misplacedTilesHeuristic, 3, 1, 2, 4, 0, 5, 6, 7, 8));
		Path path = graph.aStarExpansion();

		System.out.println(path.toStringFullPath());

		assert(path.getLeafVertex().isSolution());
	}

	public void unsolveableBoardCase2() {
		// this board seems solveable, but it can only move 0 left, or down which both lead to unsolveable boards
		Graph graph = new Graph(new GameState(misplacedTilesHeuristic, 2, 1, 0, 3, 4, 5, 6, 7, 8));
		Path path = graph.aStarExpansion();

		System.out.println(path.toStringFullPath());

		assert(path.getClass() == NullPath.class);
	}
}
