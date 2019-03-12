package n_queens_local_search;

public class UnitTest {
	public static void main(String[] args) {
		UnitTest suite = new UnitTest();
		suite.testSolutionChecker();
		suite.testSolutionChecker2();
	}

	public void testSolutionChecker() {
		// 6 7 5 2 0 1 3 4
		GameState gs = new GameState(6, 7, 5, 2, 0, 1, 3, 4);
		assert(!gs.isSolution()); // this is not a solution to n queens
	}

	public void testSolutionChecker2() {
		// 5 3 1 7 2 8 6 4
		GameState gs = new GameState(5, 3, 1, 7, 2, 8, 6, 4);
		assert(gs.isSolution()); // this is a solution to n queens
	}
}
