package n_queens_local_search;

public class UnitTestGeneticAlgorithm {
    public static void main(String[] args) {
        UnitTestGeneticAlgorithm suite = new UnitTestGeneticAlgorithm();
//        suite.checkFitness();
//        suite.testGE();
        suite.testGEBiggerBoard();
//        suite.batchTestGE();
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

    private void batchTestGE() {
        for (int i = 0; i < 1000; ++i) {
            GeneticAlgorithm ge = new GeneticAlgorithm(25, 100);
            ge.geneticAlgorithm();
        }
    }
}
