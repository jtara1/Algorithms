package n_queens_local_search;

import java.util.ArrayList;

public class TestGeneticAlgorithm {
    public static void main(String[] args) {
        TestGeneticAlgorithm suite = new TestGeneticAlgorithm();
//        suite.checkFitness();
//        suite.testGE();
//        suite.testGEBiggerBoard();
        suite.batchTest(5);
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
            GeneticAlgorithm sa = new GeneticAlgorithm(8, 100);
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

        for (GeneticAlgorithm sa : algos) {
            System.out.println(sa.getBestState());
            System.out.println(sa.getRuntime());
        }
    }
}
