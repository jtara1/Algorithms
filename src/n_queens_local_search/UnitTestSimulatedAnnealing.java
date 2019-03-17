package n_queens_local_search;

public class UnitTestSimulatedAnnealing {
    public static void main(String[] args) {
        UnitTestSimulatedAnnealing suite = new UnitTestSimulatedAnnealing();
//        suite.testSimulatedAnnealing();
        suite.batchTest(500);
    }

    public void testSimulatedAnnealing() {
//		SimulatedAnnealing sa = new SimulatedAnnealing(); // game state of random n queens state of size 8
        SimulatedAnnealing sa = new SimulatedAnnealing(GameState.trulyRandomState(24));
        State finalState = sa.simulatedAnnealing();
        System.out.println(finalState);
        System.out.println(sa.getInitialState());
    }

    public void batchTest(int testCases) {
        for (int i = 0; i < testCases; ++i) {
            SimulatedAnnealing sa = new SimulatedAnnealing(GameState.trulyRandomState(25));
            sa.start();
        }
    }
}
