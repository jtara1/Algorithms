package n_queens_local_search;

import java.util.ArrayList;

public class GeneticAlgorithm2 extends GeneticAlgorithm {
    public static ArrayList<State> solutions = new ArrayList<>();

    public GeneticAlgorithm2(int boardSize, int populationSize) {
        super(boardSize, populationSize);
    }

    public State geneticAlgorithm() {
        return geneticAlgorithm(createPopulationWithSomeSolutions());
    }

    public ArrayList<State> createPopulationWithSomeSolutions() {
        ArrayList<State> population = new ArrayList<>();

//        if (solutions.size() < 4) {
//
//        } else {
        GameState2 state_pointer = new GameState2(boardSize);

//            if (solutions.size() < 8) {
//                while (!state_pointer.isSolution()) {
//                    state_pointer = (GameState2)state_pointer.getRandomNeighbor();
//                }
//                System.out.println("create another solution for pop");
//                population.add(state_pointer);
//                solutions.add(state_pointer);
//            }

        for (int i = 0; i < 7; ++i) {
            population.add(GameState.trulyRandomState(boardSize));
        }
        population.add(ArrayUtils.sample(solutions));

//        }

        return population;
    }
}
