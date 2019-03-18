package n_queens_local_search;

import java.util.ArrayList;

public class UnitTestSimulatedAnnealing {
    public static void main(String[] args) {
        UnitTestSimulatedAnnealing suite = new UnitTestSimulatedAnnealing();
//        suite.testSimulatedAnnealing();
//        suite.testsSimulatedAnnealing2(25);
        suite.batchTest(5);
    }

    public void testSimulatedAnnealing() {
//		SimulatedAnnealing sa = new SimulatedAnnealing(); // game state of random n queens state of size 8
        SimulatedAnnealing sa = new SimulatedAnnealing(GameState.trulyRandomState(24));
        State finalState = sa.simulatedAnnealing();
        System.out.println(finalState);
        System.out.println(sa.getInitialState());
    }

    public void testsSimulatedAnnealing2(int boardSize) {
        SimulatedAnnealing sa = new SimulatedAnnealing(new GameState2(boardSize));
        State finalState = sa.simulatedAnnealing();
        System.out.println(sa.getInitialState());
        System.out.println(finalState);
    }

    public void batchTest(int testCases) {
        ArrayList<SimulatedAnnealing> algos = new ArrayList<>();

        for (int i = 0; i < testCases; ++i) {
//            SimulatedAnnealing sa = new SimulatedAnnealing(GameState.trulyRandomState(25));
            SimulatedAnnealing sa = new SimulatedAnnealing(new GameState2(25));
            sa.start();
            algos.add(sa);
        }

        boolean done = false;
        while (!done) {
            try {
                Thread.sleep(10000);
                boolean maybeDone = true;

                for (SimulatedAnnealing sa : algos) {
                    if (sa.getThread().isAlive()) maybeDone = false;
                }

                done = maybeDone;
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }

        System.out.println("done");

        for (SimulatedAnnealing sa : algos) {
            System.out.println(sa.getBestState());
        }
    }
}
