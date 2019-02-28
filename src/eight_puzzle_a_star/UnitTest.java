package eight_puzzle_a_star;

public class UnitTest {
	public static void main(String[] args) {
		UnitTest suite = new UnitTest();
		suite.case1();
	}

	public boolean case1() {
		Heuristic misplacedTilesHeuristic = new MisplacedTilesHeuristic();

		Graph graph = new Graph(new GameState(misplacedTilesHeuristic, 3, 1, 2, 4, 0, 5, 6, 7, 8));
		Path path = graph.aStarExpansion();

		return true;
	}
}
