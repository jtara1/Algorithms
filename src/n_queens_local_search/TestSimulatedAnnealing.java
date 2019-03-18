package n_queens_local_search;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class TestSimulatedAnnealing {
    public static void main(String[] args) {
        TestSimulatedAnnealing suite = new TestSimulatedAnnealing();
//        suite.testSimulatedAnnealing();
//        suite.testsSimulatedAnnealing2(25);
        suite.batchTest(10);
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
        int ranInParallel = 2;

        for (int iteration = 0; iteration < testCases; iteration += ranInParallel) {
            ArrayList<SimulatedAnnealing> algos = new ArrayList<>();

            for (int i = 0; i < ranInParallel; ++i) {
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

//            System.out.println("all threads done\n------------------------------------------");
//            GameState2.solutionAttempts = new HashSet<>();
//            for (SimulatedAnnealing sa : algos) {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            for (int i = 0; i < algos.size(); ++i) {
                SimulatedAnnealing sa = algos.get(i);
//                GameState2.solutionAttempts.add(sa.toString());
//                System.out.println(sa.getBestState() + ", runtime (s) = " + sa.getRuntime() + ", search cost = " );
                System.out.println(sa.getBestState() + "," + sa.getRuntime() + "," + sa.getSearchCost() + "," + (iteration + i) + "," + (dateFormat.format(new Date())));
            }
        }
    }
}
