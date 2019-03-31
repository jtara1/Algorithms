package n_queens_local_search;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TestGeneticAlgorithm {
    public static void main(String[] args) {
        TestGeneticAlgorithm suite = new TestGeneticAlgorithm();
//        suite.checkFitness();
//        suite.testGE();
//        suite.testGEBiggerBoard();
//        suite.batchTest(5);
        suite.batchTest2(5);
    }

    public void checkFitness() {
        // 4 4 4 1 6 4 4 6
        GameState gs = new GameState(4, 4, 4, 1, 6, 4, 4, 6);
        System.out.println(gs.fitness());
    }

    public void testGE() {
        GeneticAlgorithm algo = new GeneticAlgorithm(8, 50);
        State solution = algo.geneticAlgorithm();
        System.out.println(solution);
    }

    private void testGEBiggerBoard() {
        GeneticAlgorithm algo = new GeneticAlgorithm(25, 300);
        State solution = algo.geneticAlgorithm();
        System.out.println(solution);
    }

    public void batchTest(int testCases) {
        ArrayList<GeneticAlgorithm> algos = new ArrayList<>();

        for (int i = 0; i < testCases; ++i) {
//            SimulatedAnnealing sa = new SimulatedAnnealing(GameState.trulyRandomState(25));
//            GeneticAlgorithm sa = new GeneticAlgorithm(new GameState2(25));
            GeneticAlgorithm sa = new GeneticAlgorithm(25, 5);
            sa.start();
            algos.add(sa);
        }

        boolean done = false;
        while (!done) {
            try {
                Thread.sleep(10000);
                boolean maybeDone = true;

                for (GeneticAlgorithm sa : algos) {
                    if (sa.getThread().isAlive()) maybeDone = false;
                }

                done = maybeDone;
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }

        System.out.println("done");

        for (int i = 0; i < algos.size(); ++i) {
            GeneticAlgorithm sa = algos.get(i);
            System.out.println(sa.getBestState() + "," + sa.getRuntime() + "," + sa.getSearchCost() + "," + i + "," + sa.getBestState().isSolution() + "," + sa.getBestState().fitness());
        }
    }

    public void batchTest2(int testCases) {
        ArrayList<GeneticAlgorithm2> algos = new ArrayList<>();
        int boardSize = 8;

        for (int i = 0; i < 3; ++i) {
            GameState2 state_pointer = new GameState2(boardSize);

            while (!state_pointer.isSolution()) {
                state_pointer = (GameState2)state_pointer.getRandomNeighbor();
            }

            System.out.println("created one part of the population");
            GeneticAlgorithm2.solutions.add(state_pointer);
//            population.add(state_pointer);
//            population.add(GameState.trulyRandomState(boardSize));
        }

        for (int i = 0; i < testCases; ++i) {
//            SimulatedAnnealing sa = new SimulatedAnnealing(GameState.trulyRandomState(25));
//            GeneticAlgorithm sa = new GeneticAlgorithm(new GameState2(25));
            GeneticAlgorithm2 sa = new GeneticAlgorithm2(boardSize, 5);
            sa.start();
            algos.add(sa);
        }

        boolean done = false;
        while (!done) {
            try {
                Thread.sleep(10000);
                boolean maybeDone = true;

                for (GeneticAlgorithm2 sa : algos) {
                    if (sa.getThread().isAlive()) maybeDone = false;
                }

                done = maybeDone;
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }

        System.out.println("done");

        for (int i = 0; i < algos.size(); ++i) {
            GeneticAlgorithm2 sa = algos.get(i);
            System.out.println(sa.getBestState() + "," + sa.getRuntime() + "," + sa.getSearchCost() + "," + i + "," + sa.getBestState().isSolution() + "," + sa.getBestState().fitness());
        }
    }
}
