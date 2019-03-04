package eight_puzzle_a_star;

public class UnitTestGameState {
	public static MisplacedTilesHeuristic misplacedTilesHeuristic = new MisplacedTilesHeuristic();

	public static void main(String[] args) {
		UnitTestGameState suite = new UnitTestGameState();
		suite.case1();
		suite.case2();
	}

	private void case1() {
		GameState gs = new GameState(misplacedTilesHeuristic, 7, 1, 2, 5, 0, 9, 8, 3, 6);
		System.out.println(gs.isSolveable());
		assert(!gs.isSolveable());
	}

	private void case2() {
		// 6 1 2 4 0 8 7 3 5
		GameState gs = new GameState(misplacedTilesHeuristic, 6, 1, 2, 4, 0, 8, 7, 3, 5);
		System.out.println(gs.isSolveable());
		assert(!gs.isSolveable());
	}
}
