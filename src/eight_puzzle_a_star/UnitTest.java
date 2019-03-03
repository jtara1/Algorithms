package eight_puzzle_a_star;

public class UnitTest {
	private Heuristic misplacedTilesHeuristic = new MisplacedTilesHeuristic();
	private Heuristic distanceHeuristic = new DistanceHeuristic();

	public static void main(String[] args) {
		UnitTest suite = new UnitTest();
		suite.case1();
	}

	public boolean case1() {
		Graph graph = new Graph(new GameState(misplacedTilesHeuristic, 3, 1, 2, 4, 0, 5, 6, 7, 8));
		Path path = graph.aStarExpansion();

		System.out.println(path.toStringFullPath());

		assert(path.getLeafVertex().isSolution());
	}

	public boolean unsolveableBoardCase2() {
		return true;2
	}
}
