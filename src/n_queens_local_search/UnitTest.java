package n_queens_local_search;

public class UnitTest {
	public static void main(String[] args) {
		UnitTest suite = new UnitTest();
		suite.testSolutionChecker();
		suite.testSolutionChecker2();
		suite.testSimulatedAnnealing();
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

	public void testSimulatedAnnealing() {
//		SimulatedAnnealing sa = new SimulatedAnnealing(); // game state of random n queens state of size 8
		SimulatedAnnealing sa = new SimulatedAnnealing(GameState.trulyRandomState(8));
		State finalState = sa.start();
		System.out.println(finalState);
		System.out.println(sa.getInitialState());
	}
}
